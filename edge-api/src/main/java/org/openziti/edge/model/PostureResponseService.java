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
import java.util.Objects;
import java.util.StringJoiner;

/** PostureResponseService */
@JsonPropertyOrder({
    PostureResponseService.JSON_PROPERTY_ID,
    PostureResponseService.JSON_PROPERTY_NAME,
    PostureResponseService.JSON_PROPERTY_POSTURE_QUERY_TYPE,
    PostureResponseService.JSON_PROPERTY_TIMEOUT,
    PostureResponseService.JSON_PROPERTY_TIMEOUT_REMAINING
})
@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        date = "2024-06-07T09:12:49.062759-04:00[America/New_York]",
        comments = "Generator version: 7.6.0")
public class PostureResponseService {
    public static final String JSON_PROPERTY_ID = "id";
    private String id;

    public static final String JSON_PROPERTY_NAME = "name";
    private String name;

    public static final String JSON_PROPERTY_POSTURE_QUERY_TYPE = "postureQueryType";
    private String postureQueryType;

    public static final String JSON_PROPERTY_TIMEOUT = "timeout";
    private Integer timeout;

    public static final String JSON_PROPERTY_TIMEOUT_REMAINING = "timeoutRemaining";
    private Integer timeoutRemaining;

    public PostureResponseService() {}

    public PostureResponseService id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getId() {
        return id;
    }

    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setId(String id) {
        this.id = id;
    }

    public PostureResponseService name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getName() {
        return name;
    }

    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setName(String name) {
        this.name = name;
    }

    public PostureResponseService postureQueryType(String postureQueryType) {
        this.postureQueryType = postureQueryType;
        return this;
    }

    /**
     * Get postureQueryType
     *
     * @return postureQueryType
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_POSTURE_QUERY_TYPE)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPostureQueryType() {
        return postureQueryType;
    }

    @JsonProperty(JSON_PROPERTY_POSTURE_QUERY_TYPE)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setPostureQueryType(String postureQueryType) {
        this.postureQueryType = postureQueryType;
    }

    public PostureResponseService timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Get timeout
     *
     * @return timeout
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_TIMEOUT)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getTimeout() {
        return timeout;
    }

    @JsonProperty(JSON_PROPERTY_TIMEOUT)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public PostureResponseService timeoutRemaining(Integer timeoutRemaining) {
        this.timeoutRemaining = timeoutRemaining;
        return this;
    }

    /**
     * Get timeoutRemaining
     *
     * @return timeoutRemaining
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_TIMEOUT_REMAINING)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getTimeoutRemaining() {
        return timeoutRemaining;
    }

    @JsonProperty(JSON_PROPERTY_TIMEOUT_REMAINING)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTimeoutRemaining(Integer timeoutRemaining) {
        this.timeoutRemaining = timeoutRemaining;
    }

    /** Return true if this postureResponseService object is equal to o. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostureResponseService postureResponseService = (PostureResponseService) o;
        return Objects.equals(this.id, postureResponseService.id)
                && Objects.equals(this.name, postureResponseService.name)
                && Objects.equals(this.postureQueryType, postureResponseService.postureQueryType)
                && Objects.equals(this.timeout, postureResponseService.timeout)
                && Objects.equals(this.timeoutRemaining, postureResponseService.timeoutRemaining);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, postureQueryType, timeout, timeoutRemaining);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PostureResponseService {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    postureQueryType: ").append(toIndentedString(postureQueryType)).append("\n");
        sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
        sb.append("    timeoutRemaining: ").append(toIndentedString(timeoutRemaining)).append("\n");
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

        // add `id` to the URL query string
        if (getId() != null) {
            joiner.add(
                    String.format(
                            "%sid%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(String.valueOf(getId()), StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        // add `name` to the URL query string
        if (getName() != null) {
            joiner.add(
                    String.format(
                            "%sname%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(String.valueOf(getName()), StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        // add `postureQueryType` to the URL query string
        if (getPostureQueryType() != null) {
            joiner.add(
                    String.format(
                            "%spostureQueryType%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(
                                            String.valueOf(getPostureQueryType()),
                                            StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        // add `timeout` to the URL query string
        if (getTimeout() != null) {
            joiner.add(
                    String.format(
                            "%stimeout%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(String.valueOf(getTimeout()), StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        // add `timeoutRemaining` to the URL query string
        if (getTimeoutRemaining() != null) {
            joiner.add(
                    String.format(
                            "%stimeoutRemaining%s=%s",
                            prefix,
                            suffix,
                            URLEncoder.encode(
                                            String.valueOf(getTimeoutRemaining()),
                                            StandardCharsets.UTF_8)
                                    .replaceAll("\\+", "%20")));
        }

        return joiner.toString();
    }
}
