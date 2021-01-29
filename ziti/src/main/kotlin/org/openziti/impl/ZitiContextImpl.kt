/*
 * Copyright (c) 2018-2021 NetFoundry, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openziti.impl

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select
import org.openziti.*
import org.openziti.api.*
import org.openziti.identity.Identity
import org.openziti.net.Channel
import org.openziti.net.ZitiServerSocketChannel
import org.openziti.net.ZitiSocketChannel
import org.openziti.net.dns.ZitiDNSManager
import org.openziti.net.nio.AsychChannelSocket
import org.openziti.posture.PostureService
import org.openziti.util.Logged
import org.openziti.util.ZitiLog
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URI
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates
import kotlin.random.Random
import org.openziti.api.Identity as ApiIdentity

/**
 * Object maintaining current Ziti session.
 *
 */
internal class ZitiContextImpl(internal val id: Identity, enabled: Boolean) : ZitiContext, Identity by id,
    CoroutineScope, Logged by ZitiLog() {

    private var _enabled: Boolean by Delegates.observable(enabled) { _, _, isEnabled ->
        if (isEnabled) {
            if (statusCh.value == ZitiContext.Status.Disabled)
                statusCh.value = ZitiContext.Status.Active
        } else {
            statusCh.value = ZitiContext.Status.Disabled
        }
    }

    private var refreshDelay = Duration.ofMinutes(5).toMillis()

    private val supervisor = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + supervisor

    private var apiSession: ApiSession? = null

    private var apiId: ApiIdentity? = null
    private val controller: Controller = Controller(URI.create(id.controller()).toURL(), sslContext(), trustManager())
    private val postureService = PostureService()

    private val statusCh: MutableStateFlow<ZitiContext.Status>
            = MutableStateFlow(if (enabled) ZitiContext.Status.Loading else ZitiContext.Status.Disabled)

    private val serviceCh: MutableSharedFlow<ZitiContext.ServiceEvent> = MutableSharedFlow()

    private val servicesLoaded = CompletableDeferred<Unit>()
    private val servicesByName = mutableMapOf<String, Service>()
    private val servicesById = mutableMapOf<String, Service>()
    private val servicesByAddr = mutableMapOf<InetSocketAddress, Service>()
    private val addrById = mutableMapOf<String, InetSocketAddress>()

    data class SessionKey (val serviceId: String, val type: SessionType)
    private val networkSessions = ConcurrentHashMap<SessionKey, Session>()

    private val connCounter = AtomicInteger(0)

    init {
        this._enabled = enabled

        launch {
            statusCh.collect {
                d { "${this@ZitiContextImpl} transitioned to $it" }
                if (it == ZitiContext.Status.Disabled) {
                    stop()
                }
            }
        }

        start()
    }

    override fun getId(): ApiIdentity? = apiId

    override fun getStatus() = statusCh.value
    override fun statusUpdates() = statusCh

    override fun serviceUpdates(): Flow<ZitiContext.ServiceEvent> = flow {
        emitAll(servicesByName.values.map {
            ZitiContext.ServiceEvent(it, ZitiContext.ServiceUpdate.Available)
        }.asFlow())
        emitAll(serviceCh)
    }

    override fun getServiceTerminators(service: Service): Collection<ServiceTerminator> = runBlocking {
        controller.getServiceTerminators(service).toCollection(mutableListOf())
    }

    private fun updateStatus(s: ZitiContext.Status) {
        if (statusCh.value != ZitiContext.Status.Disabled) {
            statusCh.value = s
        }
    }

    override fun setEnabled(v: Boolean) {
        _enabled = v
    }

    override fun dial(serviceName: String): ZitiConnection {
        val conn = open() as ZitiSocketChannel
        conn.connect(ZitiAddress.Dial(serviceName)).get()
        return conn
    }

    internal fun dialById(serviceId: String): ZitiConnection =
        servicesById[serviceId]?.let {
            dial(it.name)
        } ?: throw ZitiException(Errors.ServiceNotAvailable)


    internal fun dial(host: String, port: Int): ZitiConnection {
        val service = getService(host, port)
        return dial(service.name)
    }

    override fun connect(host: String, port: Int): Socket {
        val ch = open()
        val s = getService(host, port)
        ch.connect(ZitiAddress.Dial(s.name)).get()
        return AsychChannelSocket(ch)
    }

    fun start(): Job = launch {
        runner()
    }

    private suspend fun runner() {
        while(true) {
            apiSession = runCatching { login() }.getOrElse {
                e("failed to login, cannot continue")
                updateStatus(ZitiContext.Status.NotAuthorized(it))
                throw it
            }

            updateStatus(ZitiContext.Status.Active)

            val apiSessionUpdate = maintainApiSession()
            val serviceUpdate = runServiceUpdates()

            val finisher = select<Job> {
                apiSessionUpdate.onJoin { apiSessionUpdate }
                serviceUpdate.onJoin { serviceUpdate }
            }

            finisher.invokeOnCompletion { ex ->
                w {"$finisher is completed due to $ex"}
            }

            listOf(apiSessionUpdate, serviceUpdate).forEach {
                it.cancelAndJoin()
            }
        }
    }

    fun stop() {
        val copy = channels.values

        runBlocking {
            copy.forEach { ch ->
                runCatching {
                    d{"closing ${ch.name}"}
                    ch.close()
                }
            }
        }
    }

    override fun destroy() {
        d{"stopping networking"}
        stop()
        d{"stopping controller"}
        runCatching { controller.shutdown() }
        d{"shutting down"}
        runBlocking { supervisor.cancelAndJoin() }
        d{"ziti context is finished"}
    }

    internal suspend fun login(): ApiSession {
        var attempt = 0
        statusCh.value = ZitiContext.Status.Loading
        while(true) {
            if (attempt > 0) {
                val backoff = Random.nextInt(0, attempt) % 6
                val delaySeconds = (1 shl backoff) * 5
                d{ "retrying login() after ${delaySeconds}s (attempt=$attempt)"}
                delay(delaySeconds * 1000L)
            }

            try {
                return controller.login()
            } catch (ex: Exception) {
                if (ex is ZitiException && ex.code == Errors.NotAuthorized) {
                    throw ex
                }
                updateStatus(ZitiContext.Status.Unavailable(ex))
                w { "failed to login: ${ex.localizedMessage}" }
                attempt++
                continue
            }
        }
    }

    private fun maintainApiSession() = launch {
        while (apiSession != null) {
            val refreshDelay = apiSession?.let { it.expiresAt.time - it.updatedAt.time - 10} ?: 0
            if (refreshDelay > 0) {
                v{"waiting for refresh $refreshDelay seconds"}
                delay(refreshDelay * 1000)
            }
            apiSession = controller.currentApiSession()
        }
    }

    private fun runServiceUpdates() = launch {
        var lastUpdate = Date(0)
        while (true) {
            d("[${id.name()}] slept and restarting on t[${Thread.currentThread().name}]")
            val updt = controller.getServiceUpdates()

            if (updt.lastChangeAt.after(lastUpdate)) {
                lastUpdate = updt.lastChangeAt

                val services = controller.getServices().toList()
                processServiceUpdates(services)
                d("[${id.name()}] got ${services.size} services on t[${Thread.currentThread().name}]")

                val edgeRouters = mutableSetOf<EdgeRouter>()
                controller.getSessions().collect {
                    val s = updateSession(it)
                    s?.let {
                        edgeRouters.addAll(it.edgeRouters!!)
                    }
                }

                connectAll(edgeRouters)
            }
            delay(refreshDelay)
        }
    }

    fun logout() = runBlocking { controller.logout() }

    fun checkServicesLoaded() = runBlocking {
        try {
            withTimeout(30_000) {
                servicesLoaded.await()
            }
        } catch (tex: TimeoutCancellationException) {
            e("failed to load services: $tex")
        }
    }

    internal suspend fun getNetworkSession(service: Service, st: SessionType): Session {
        d("getNetworkSession(${service.name})")

        _enabled || throw ZitiException(Errors.ServiceNotAvailable)

        checkServicesLoaded()

        return networkSessions.getOrPut(SessionKey(service.id, st)) {
            val netSession = controller.createNetSession(service, st)
            t("received $netSession for service[${service.name}]")
            netSession
        }
    }

    internal suspend fun getNetworkSession(name: String, st: SessionType): Session {
        checkServicesLoaded()

        val service = servicesByName.get(name) ?: throw ZitiException(Errors.ServiceNotAvailable)
        return getNetworkSession(service, st)

    }

    internal fun getService(name: String): Service {
        checkServicesLoaded()
        return servicesByName.get(name) ?: throw ZitiException(Errors.ServiceNotAvailable)
    }

    internal fun getService(host: String, port: Int): Service {
        checkServicesLoaded()

        return servicesByName.values.find {
            it.dns?.hostname == host && it.dns?.port == port
        } ?: throw ZitiException(Errors.ServiceNotAvailable)
    }

    internal fun nextConnId() = connCounter.incrementAndGet()

    internal val channels = ConcurrentHashMap<String, Channel>()

    private val latencyInterval = Duration.ofMinutes(1)

    internal suspend fun getChannel(ns: Session): Channel {
        val ers = ns.edgeRouters ?: throw ZitiException(Errors.EdgeRouterUnavailable)

        val addrList = ers.map { it.urls["tls"] }.filterNotNull()

        val chMap = sortedMapOf<Long,Channel>()
        val unconnected = mutableListOf<Channel>()

        for (addr in addrList) {
            val c = getChannel(addr)

            val s = c.state
            when(s) {
                is Channel.State.Connected -> chMap.put(s.latency, c)
                else -> unconnected.add(c)
            }
        }

        if (chMap.isEmpty()) {
            val selected = connectAll(unconnected) ?: throw ZitiException(Errors.EdgeRouterUnavailable)
            d{"selected $selected"}
            return selected
        } else {
            coroutineScope { launch { connectAll(unconnected) } }
            return chMap.entries.first().value
        }
    }

    internal fun getChannel(addr: String): Channel {
        return channels.computeIfAbsent(addr) {
            Channel(it, id, { -> apiSession})
        }
    }

    internal fun connectAll(routers: Collection<EdgeRouter>) = launch {
        routers.forEach {
            it.urls["tls"]?.let {
                getChannel(it)
            }
        }
    }

    internal suspend fun connectAll(chList: List<Channel>): Channel? {
        if (chList.isEmpty()) return null

        return select {
            for (ch in chList) {
                ch.connectNow().onAwait { ch }
            }
        }
    }

    // can't just replace old with new as updates do not contain session token
    // do update list of edge routers for this session
    private fun updateSession(s: Session): Session? {
        val sessionKey = SessionKey(s.service.id, s.type)
        networkSessions.containsKey(sessionKey) || return null
        return networkSessions.merge(sessionKey, s) { old, new ->
            old.apply { edgeRouters = new.edgeRouters }
        }
    }

    internal fun processServiceUpdates(services: Collection<Service>) {
        val currentIds = services.map { it.id }.toCollection(mutableSetOf())
        // check removed access

        val removeIds = servicesById.keys.filter { !currentIds.contains(it) }
        for (rid in removeIds) {
            servicesById.remove(rid)?.let {
                servicesByName.remove(it.name)
                addrById.remove(it.id)?.let { addr ->
                    servicesByAddr.remove(addr)
                }
                ZitiDNSManager.unregisterService(it)
                launch {
                    serviceCh.emit(ZitiContext.ServiceEvent(it, ZitiContext.ServiceUpdate.Unavailable))
                }
            }

            networkSessions.remove(SessionKey(rid, SessionType.Dial))
            networkSessions.remove(SessionKey(rid, SessionType.Bind))
        }

        // update
        for (s in services) {
            val oldV = servicesByName.put(s.name, s)
            if (oldV == null) {
                launch {
                    serviceCh.emit(ZitiContext.ServiceEvent(s, ZitiContext.ServiceUpdate.Available))
                }
            }
            servicesById.put(s.id, s)

            ZitiDNSManager.registerService(s)?.let { addr ->
                addrById.put(s.id, addr)
                servicesByAddr.put(addr, s)

                d("[${name()}] service[${s.name}] => $addr")
            }

            s.postureSets?.forEach {
                it.postureQueries?.forEach {
                    postureService.registerServiceCheck(s.id, it)
                }
            }
        }

        runBlocking {
            postureService.getPosture().forEach {
                controller.sendPostureResp(it)
            }
        }

        servicesLoaded.complete(Unit)
    }

    override fun open(): AsynchronousSocketChannel {
        checkActive()
        return ZitiSocketChannel(this)
    }

    override fun openServer(): AsynchronousServerSocketChannel {
        checkActive()
        return ZitiServerSocketChannel(this)
    }

    override fun toString(): String {
        val id = getId()
        return "${id?.name ?: name()}[${id?.id}]@${controller()}"
    }

    private fun checkActive() {
        if (getStatus() == ZitiContext.Status.Active)
            return

        // wait for active
        runBlocking {
            statusCh.takeWhile { it != ZitiContext.Status.Active }.collect()
        }
    }
}