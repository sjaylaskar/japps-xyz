/*
* Id: StringUtilTest.java 08-Mar-2022 4:21:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * The string util test.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class StringUtilTest {

    /**
     * Test method for {@link com.xyz.apps.ticketeer.util.StringUtil#format(java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testFormat() {

        final String text = "url/event/id/{0}";

        final String formattedText = StringUtil.format(text, 12);

        assertEquals("url/event/id/12", formattedText);
    }

}
