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

/** IdentityAuthenticatorsUpdb */
@JsonPropertyOrder({
    IdentityAuthenticatorsUpdb.JSON_PROPERTY_ID,
    IdentityAuthenticatorsUpdb.JSON_PROPERTY_USERNAME
})
@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        date = "2024-06-07T09:12:49.062759-04:00[America/New_York]",
        comments = "Generator version: 7.6.0")
public class IdentityAuthenticatorsUpdb {
    public static final String JSON_PROPERTY_ID = "id";
    private String id;

    public static final String JSON_PROPERTY_USERNAME = "username";
    private String username;

    public IdentityAuthenticatorsUpdb() {}

    public IdentityAuthenticatorsUpdb id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     */
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public String getId() {
        return id;
    }

    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setId(String id) {
        this.id = id;
    }

    public IdentityAuthenticatorsUpdb username(String username) {
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

    /** Return true if this identityAuthenticators_updb object is equal to o. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentityAuthenticatorsUpdb identityAuthenticatorsUpdb = (IdentityAuthenticatorsUpdb) o;
        return Objects.equals(this.id, identityAuthenticatorsUpdb.id)
                && Objects.equals(this.username, identityAuthenticatorsUpdb.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class IdentityAuthenticatorsUpdb {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
