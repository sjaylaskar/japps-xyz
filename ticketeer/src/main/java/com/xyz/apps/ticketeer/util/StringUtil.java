/*
* Id: StringUtil.java 08-Mar-2022 4:17:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

/**
 * The string util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class StringUtil {

    private StringUtil() {

    }

    /**
     * Format.
     *
     * @param text the text
     * @param placeHolderValues the place holder values
     * @return the formatted string
     */
    public static String format(final String text, final Object ...placeHolderValues) {
        return (ArrayUtils.isNotEmpty(placeHolderValues))
                ? MessageFormat.format(text, placeHolderValues)
                : text;
    }
}
