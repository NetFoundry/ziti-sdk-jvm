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
import java.util.Objects;
import java.util.StringJoiner;

/** ListCurrentApiSessionServiceUpdatesEnvelope */
@JsonPropertyOrder({
    ListCurrentApiSessionServiceUpdatesEnvelope.JSON_PROPERTY_DATA,
    ListCurrentApiSessionServiceUpdatesEnvelope.JSON_PROPERTY_META
})
@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        date = "2024-06-07T09:12:49.062759-04:00[America/New_York]",
        comments = "Generator version: 7.6.0")
public class ListCurrentApiSessionServiceUpdatesEnvelope {
    public static final String JSON_PROPERTY_DATA = "data";
    private CurrentApiSessionServiceUpdateList data;

    public static final String JSON_PROPERTY_META = "meta";
    private Meta meta;

    public ListCurrentApiSessionServiceUpdatesEnvelope() {}

    public ListCurrentApiSessionServiceUpdatesEnvelope data(
            CurrentApiSessionServiceUpdateList data) {
        this.data = data;
        return this;
    }

    /**
     * Get data
     *
     * @return data
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_DATA)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public CurrentApiSessionServiceUpdateList getData() {
        return data;
    }

    @JsonProperty(JSON_PROPERTY_DATA)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setData(CurrentApiSessionServiceUpdateList data) {
        this.data = data;
    }

    public ListCurrentApiSessionServiceUpdatesEnvelope meta(Meta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Get meta
     *
     * @return meta
     */
    @javax.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /** Return true if this listCurrentApiSessionServiceUpdatesEnvelope object is equal to o. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListCurrentApiSessionServiceUpdatesEnvelope listCurrentApiSessionServiceUpdatesEnvelope =
                (ListCurrentApiSessionServiceUpdatesEnvelope) o;
        return Objects.equals(this.data, listCurrentApiSessionServiceUpdatesEnvelope.data)
                && Objects.equals(this.meta, listCurrentApiSessionServiceUpdatesEnvelope.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListCurrentApiSessionServiceUpdatesEnvelope {\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
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

        // add `data` to the URL query string
        if (getData() != null) {
            joiner.add(getData().toUrlQueryString(prefix + "data" + suffix));
        }

        // add `meta` to the URL query string
        if (getMeta() != null) {
            joiner.add(getMeta().toUrlQueryString(prefix + "meta" + suffix));
        }

        return joiner.toString();
    }
}
