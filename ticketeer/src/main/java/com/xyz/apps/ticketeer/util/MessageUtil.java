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

    /** The entity field validation message key suffix. */
    private static final String ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX = "}";

    /** The entity field validation message key prefix. */
    private static final String ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX = "{";

    /** The message key. */
    public static final String METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX = "[#$#methodArgValidationMessageKeyPrefix#$#]";

    /**
     * Instantiates a new message util.
     */
    private MessageUtil() {

    }

    /**
     * To message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return the message key
     */
    public static String fromMethodArgMessageKey(final String messageKeyDenoter) {

        return (isMethodArgMessageKey(messageKeyDenoter))
            ? StringUtils.replace(messageKeyDenoter, METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX, StringUtils.EMPTY)
            : messageKeyDenoter;
    }

    /**
     * From entity field validation message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return the string
     */
    public static String fromEntityFieldValidationMessageKey(final String messageKeyDenoter) {

        if (!isEntityFieldValidationMessageKey(messageKeyDenoter)) {
            return messageKeyDenoter;
        }
        return messageKeyDenoter.substring(1, messageKeyDenoter.length() - 1);
    }

    /**
     * Checks if is method arg message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return true, if is method arg message key
     */
    public static boolean isMethodArgMessageKey(final String messageKeyDenoter) {

        return StringUtils.startsWith(messageKeyDenoter, METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX);
    }

    /**
     * Checks if is method arg or entity field message key.
     *
     * @param messageKey the message key
     * @return true, if is method arg or entity field message key
     */
    public static boolean isMethodArgOrEntityFieldMessageKey(final String messageKey) {
        return isMethodArgMessageKey(messageKey) || isEntityFieldValidationMessageKey(messageKey);
    }

    /**
     * Checks if is entity field validation message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return true, if is entity field validation message key
     */
    public static boolean isEntityFieldValidationMessageKey(final String messageKeyDenoter) {

        return StringUtils.startsWith(messageKeyDenoter, ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX)
            && StringUtils.endsWith(messageKeyDenoter, ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX);
    }

    /**
     * Default locale message.
     *
     * @param messageSource the message source
     * @param messageKeyDenoter the message key denoter
     * @param arguments the arguments
     * @return the message
     */
    public static String fromMessageSource(final MessageSource messageSource, final String messageKeyDenoter,
            final Object... arguments) {

        validate(messageSource, messageKeyDenoter);
        return messageSource.getMessage(fromEntityFieldValidationMessageKey(fromMethodArgMessageKey(messageKeyDenoter)), arguments, locale());
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
    public static String fromResourceBundle(final ResourceBundle resourceBundle, final String messageKeyDenoter,
            final Object... arguments) {

        validate(resourceBundle, messageKeyDenoter);
        return MessageFormat.format(resourceBundle.getString(fromEntityFieldValidationMessageKey(fromMethodArgMessageKey(messageKeyDenoter))), arguments);
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
