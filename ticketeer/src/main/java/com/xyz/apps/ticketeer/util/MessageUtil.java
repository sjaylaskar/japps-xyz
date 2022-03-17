/*
* Id: MessageUtil.java 13-Mar-2022 2:32:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * The message util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class MessageUtil {


    /**
     * Instantiates a new message util.
     */
    private MessageUtil() {

    }

    /**
     * Default locale message.
     *
     * @param messageSource the message source
     * @param messageKeyDenoter the message key denoter
     * @param arguments the arguments
     * @return the message
     */
    public static String fromMessageSource(final MessageSource messageSource, final String messageKeyDenoter, final Object ...arguments) {
        validate(messageSource, messageKeyDenoter);
        return messageSource.getMessage(StringUtil.toMessageKey(messageKeyDenoter), arguments, locale());
    }

    /**
     * Locale.
     *
     * @return the locale
     */
    private static Locale locale() {

        return LocaleContextHolder.getLocale();
    }

    /**
     * Message.
     *
     * @param resourceBundle the resource bundle
     * @param messageKeyDenoter the message key denoter
     * @param arguments the arguments
     * @return the message
     */
    public static String fromResourceBundle(final ResourceBundle resourceBundle, final String messageKeyDenoter, final Object ...arguments) {
        validate(resourceBundle, messageKeyDenoter);
        return MessageFormat.format(resourceBundle.getString(StringUtil.toMessageKey(messageKeyDenoter)), arguments);
    }

    /**
     * Validate.
     *
     * @param messageSource the message source
     * @param messageKey the message key
     */
    private static void validate(final MessageSource messageSource, final String messageKey) {
        if (messageSource == null) {
            throw new IllegalArgumentException("The message source cannot be null.");
        }
        validate(messageKey);
    }

    /**
     * Validate.
     *
     * @param resourceBundle the resource bundle
     * @param messageKey the message key
     */
    private static void validate(final ResourceBundle resourceBundle, final String messageKey) {
        if (resourceBundle == null) {
            throw new IllegalArgumentException("The resource bundle cannot be null.");
        }
        validate(messageKey);
    }

    /**
     * Validate.
     *
     * @param messageKey the message key
     */
    private static void validate(final String messageKey) {
        if (StringUtils.isBlank(messageKey)) {
            throw new IllegalArgumentException("The message key cannot be blank.");
        }
    }
}
