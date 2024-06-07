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

package org.openziti.edge;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        date = "2024-06-07T09:12:49.062759-04:00[America/New_York]",
        comments = "Generator version: 7.6.0")
public class RFC3339DateFormat extends DateFormat {
    private static final long serialVersionUID = 1L;
    private static final TimeZone TIMEZONE_Z = TimeZone.getTimeZone("UTC");

    private final StdDateFormat fmt =
            new StdDateFormat().withTimeZone(TIMEZONE_Z).withColonInTimeZone(true);

    public RFC3339DateFormat() {
        this.calendar = new GregorianCalendar();
        this.numberFormat = new DecimalFormat();
    }

    @Override
    public Date parse(String source) {
        return parse(source, new ParsePosition(0));
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        return fmt.parse(source, pos);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return fmt.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
