/*
* Id: MessageUtil.java 13-Mar-2022 2:32:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.MessageSource;

/**
 * The message util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class MessageUtil {

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
    public static final String defaultLocaleMessage(final MessageSource messageSource, final String messageKeyDenoter, final Object[] arguments) {
        return messageSource.getMessage(StringUtil.toMessageKey(messageKeyDenoter), arguments, Locale.getDefault());
    }

    /**
     * Default locale message.
     *
     * @param messageSource the message source
     * @param messageKeyDenoter the message key denoter
     * @param argument the argument
     * @return the string
     */
    public static final String defaultLocaleMessage(final MessageSource messageSource, final String messageKeyDenoter, final Object argument) {
        return defaultLocaleMessage(messageSource, messageKeyDenoter, new Object[] {argument});
    }

    /**
     * Message.
     *
     * @param resourceBundle the resource bundle
     * @param messageKey the message key
     * @param arguments the arguments
     * @return the string
     */
    public static final String message(final ResourceBundle resourceBundle, final String messageKey, final Object ...arguments) {
        return MessageFormat.format(resourceBundle.getString(messageKey), arguments);
    }
}
