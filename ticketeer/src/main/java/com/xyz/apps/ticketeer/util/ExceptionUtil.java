/*
* Id: ExceptionUtil.java 13-Mar-2022 1:36:13 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import org.apache.logging.log4j.Logger;

/**
 * The exception util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ExceptionUtil {

    private ExceptionUtil() {

    }

    /**
     * Log and throw.
     *
     * @param exception the exception
     * @param logger the logger
     * @throws Exception the exception
     */
    public static void logAndThrow(final Exception exception,
                                   final Logger logger) throws Exception {
        logger.error(exception);
        throw exception;
    }
}
