/*
* Id: LocalizedException.java 14-Mar-2022 10:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import java.util.ResourceBundle;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.xyz.apps.ticketeer.util.MessageUtil;

/**
 * The localized exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class LocalizedException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6712226858643691102L;

    public abstract HttpStatus httpStatus();

    /**
     * This is deprecated and will be removed in future.
     * Use the localized constructors that take a resource bundle, message key and message arguments as parameters.
     *
     * @param message the message.
     */
    @Deprecated(forRemoval = true)
    public LocalizedException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new localized exception.
     *
     * @param resourceBundle the resource bundle
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public LocalizedException(final ResourceBundle resourceBundle, final String messageKey, final Object ...messageArguments) {
        super(MessageUtil.fromResourceBundle(resourceBundle, messageKey, messageArguments));
    }

    /**
     * Instantiates a new localized exception.
     *
     * @param messageSource the message source
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public LocalizedException(final MessageSource messageSource, final String messageKey, final Object ...messageArguments) {
        super(MessageUtil.fromMessageSource(messageSource, messageKey, messageArguments));
    }
}
