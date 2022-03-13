/*
* Id: StringUtil.java 08-Mar-2022 4:17:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * The string util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class StringUtil {

    private StringUtil() {

    }

    /** The message key. */
    public static final String MESSAGE_KEY = "[#$#messageKey#$#]";

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

    /**
     * To message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return the message key
     */
    public static String toMessageKey(final String messageKeyDenoter) {
        return StringUtils.replace(messageKeyDenoter, MESSAGE_KEY, StringUtils.EMPTY);
    }
}
