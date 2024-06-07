/*
 * Ziti Edge Client
 * OpenZiti Edge Client API
 *
 * The version of the OpenAPI document: 0.26.20
 * Contact: help@openziti.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.openziti.edge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A generic authenticate object meant for use with the /authenticate path. Required fields depend
 * on authentication method.
 */
@JsonPropertyOrder({
    Authenticate.JSON_PROPERTY_CONFIG_TYPES,
    Authenticate.JSON_PROPERTY_ENV_INFO,
    Authenticate.JSON_PROPERTY_PASSWORD,
    Authenticate.JSON_PROPERTY_SDK_INFO,
    Authenticate.JSON_PROPERTY_USERNAME
})
@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        date = "2024-06-07T09:12:49.062759-04:00[America/New_York]",
        comments = "Generator version: 7.6.0")
public class Authenticate {
    public static final String JSON_PROPERTY_CONFIG_TYPES = "configTypes";
    private List<String> configTypes = new ArrayList<>();

    public static final String JSON_PROPERTY_ENV_INFO = "envInfo";
    private EnvInfo envInfo;

    public static final String JSON_PROPERTY_PASSWORD = "password";
    private String password;

    public static final String JSON_PROPERTY_SDK_INFO = "sdkInfo";
    private SdkInfo sdkInfo;

    public static final String JSON_PROPERTY_USERNAME = "username";
    private String username;

    public Authenticate() {}

    public Authenticate configTypes(List<String> configTypes) {
        this.configTypes = configTypes;
        return this;
    }

    public Authenticate addConfigTypesItem(String configTypesItem) {
        if (this.configTypes == null) {
            this.configTypes = new ArrayList<>();
        }
        this.configTypes.add(configTypesItem);
        return this;
    }

    /**
     * Specific configuration types that should be returned
     *
     * @return configTypes
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_CONFIG_TYPES)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public List<String> getConfigTypes() {
        return configTypes;
    }

    @JsonProperty(JSON_PROPERTY_CONFIG_TYPES)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setConfigTypes(List<String> configTypes) {
        this.configTypes = configTypes;
    }

    public Authenticate envInfo(EnvInfo envInfo) {
        this.envInfo = envInfo;
        return this;
    }

    /**
     * Get envInfo
     *
     * @return envInfo
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_ENV_INFO)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public EnvInfo getEnvInfo() {
        return envInfo;
    }

    @JsonProperty(JSON_PROPERTY_ENV_INFO)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setEnvInfo(EnvInfo envInfo) {
        this.envInfo = envInfo;
    }

    public Authenticate password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public String getPassword() {
        return password;
    }

    @JsonProperty(JSON_PROPERTY_PASSWORD)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setPassword(String password) {
        this.password = password;
    }

    public Authenticate sdkInfo(SdkInfo sdkInfo) {
        this.sdkInfo = sdkInfo;
        return this;
    }

    /**
     * Get sdkInfo
     *
     * @return sdkInfo
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_SDK_INFO)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public SdkInfo getSdkInfo() {
        return sdkInfo;
    }

    @JsonProperty(JSON_PROPERTY_SDK_INFO)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setSdkInfo(SdkInfo sdkInfo) {
        this.sdkInfo = sdkInfo;
    }

    public Authenticate username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get username
     *
     * @return username
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_USERNAME)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public String getUsername() {
        return username;
    }

    @JsonProperty(JSON_PROPERTY_USERNAME)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setUsername(String username) {
        this.username = username;
    }

    /** Return true if this authenticate object is equal to o. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Authenticate authenticate = (Authenticate) o;
        return Objects.equals(this.configTypes, authenticate.configTypes)
                && Objects.equals(this.envInfo, authenticate.envInfo)
                && Objects.equals(this.password, authenticate.password)
                && Objects.equals(this.sdkInfo, authenticate.sdkInfo)
                && Objects.equals(this.username, authenticate.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configTypes, envInfo, password, sdkInfo, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Authenticate {\n");
        sb.append("    configTypes: ").append(toIndentedString(configTypes)).append("\n");
        sb.append("    envInfo: ").append(toIndentedString(envInfo)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    sdkInfo: ").append(toIndentedString(sdkInfo)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first
     * line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Convert the instance into URL query string.
     *
     * @return URL query string
     */
    public String toUrlQueryString() {
        return toUrlQueryString(null);
    }

    /**
     * Convert the instance into URL query string.
     *
     * @param prefix prefix of the query string
     * @return URL query string
     */
    public String toUrlQueryString(String prefix) {
        String suffix = "";
        String containerSuffix = "";
        String containerPrefix = "";
        if (prefix == null) {
            // style=form, explode=true, e.g. /pet?name=cat&type=manx
            prefix = "";
        } else {
            // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
            prefix = prefix + "[";
            suffix = "]";
            containerSuffix = "]";
            containerPrefix = "[";
        }

        StringJoiner joiner = new StringJoiner("&");

        // add `configTypes` to the URL query string
        if (getConfigTypes() != null) {
            for (int i = 0; i < getConfigTypes().size(); i++) {
                joiner.add(
                        String.format(
                                "%sconfigTypes%s%s=%s",
                                prefix,
                                suffix,
                                "".equals(suffix)
                                        ? ""
                                        : String.format(
                                                "%s%d%s", containerPrefix, i, containerSuffix),
                                URLEncoder.encode(
                                                String.valueOf(getConfigTypes().get(i)),
                                                StandardCharsets.UTF_8)
                                        .replaceAll("\\+", "%20")));
            }
        }

        // add `envInfo` to the URL query string
        if (getEnvInfo() != null) {
            joiner.add(getEnvInfo().toUrlQueryString(prefix + "envInfo" + suffix));
        }

        // add `password` to the URL query string
        if (getPassword() != null) {
            joiner.add(
                    String.format(
                            "%spassword%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(String.valueOf(getPassword()), StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        // add `sdkInfo` to the URL query string
        if (getSdkInfo() != null) {
            joiner.add(getSdkInfo().toUrlQueryString(prefix + "sdkInfo" + suffix));
        }

        // add `username` to the URL query string
        if (getUsername() != null) {
            joiner.add(
                    String.format(
                            "%susername%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(String.valueOf(getUsername()), StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        return joiner.toString();
    }
}
