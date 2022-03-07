/*
* Id: LocalDateTimeFormatUtilTest.java 07-Mar-2022 6:31:45 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;



/**
 * The local date time format util test.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
class LocalDateTimeFormatUtilTest {

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalDate)}.
     */
    @Test
    void testFormatLocalDate() {

        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalDateTime)}.
     */
    @Test
    void testFormatLocalDateTime() {

        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#format(java.time.LocalTime)}.
     */
    @Test
    void testFormatLocalTime() {

        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalDate(java.lang.String)}.
     */
    @Test
    void testParseLocalDate() {
        assertEquals(LocalDate.parse("23-03-2022", DateTimeFormatter.ISO_LOCAL_DATE), LocalDateTimeFormatUtil.parseLocalDate("23-03-2022"));
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalDateTime(java.lang.String)}.
     */
    @Test
    void testParseLocalDateTime() {

        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil#parseLocalTime(java.lang.String)}.
     */
    @Test
    void testParseLocalTime() {

        fail("Not yet implemented");
    }

}
