/*
* Id: LocalDateTimeFormatUtilTest.java 07-Mar-2022 6:31:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;


import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;



/**
 * The local date time format util test.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class LocalDateTimeFormatUtilTest {

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalDate)}.
     */
    @Test
    public void testFormatLocalDate() {

        final LocalDate localDate = LocalDate.parse("2022-03-23", DateTimeFormatter.ISO_LOCAL_DATE);
        assertEquals(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), LocalDateTimeFormatUtil.format(localDate));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalDateTime)}.
     */
    @Test
    public void testFormatLocalDateTime() {

        final LocalDateTime localDateTime = LocalDateTime.parse("2022-03-23T10:30:40", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTimeFormatUtil.format(localDateTime));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalTime)}.
     */
    @Test
    public void testFormatLocalTime() {

        final LocalTime localTime = LocalTime.parse("00:00", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME), LocalDateTimeFormatUtil.format(localTime));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalDate(java.lang.String)}.
     */
    @Test
    public void testParseLocalDate() {
        assertEquals(LocalDate.parse("2022-03-23", DateTimeFormatter.ISO_LOCAL_DATE), LocalDateTimeFormatUtil.parseLocalDate("2022-03-23"));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalDateTime(java.lang.String)}.
     */
    @Test
    public void testParseLocalDateTime() {

        assertEquals(LocalDateTime.parse("2022-03-23T10:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTimeFormatUtil.parseLocalDateTime("2022-03-23T10:30"));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalTime(java.lang.String)}.
     */
    @Test
    public void testParseLocalTime() {

        assertEquals(LocalTime.parse("23:59", DateTimeFormatter.ISO_LOCAL_TIME), LocalDateTimeFormatUtil.parseLocalTime("23:59"));
    }

}
