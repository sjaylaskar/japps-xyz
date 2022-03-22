/*
 * Id: MessageUtil.java 13-Mar-2022 2:32:00 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.util;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.util.Pair;

import lombok.extern.log4j.Log4j2;


/**
 * The message util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */

/** The log. */
@Log4j2
public final class MessageUtil {

    /** The entity field validation message key suffix. */
    public static final String ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX = "}";

    /** The entity field validation message key prefix. */
    public static final String ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX = "{";

    /**
     * Instantiates a new message util.
     */
    private MessageUtil() {

    }

    /**
     * Format property errors.
     *
     * @param messageSource the message source
     * @param propertyToInvalidValueMessagePairs the property to invalid value message pairs
     * @return the errors delimited by newline character.
     */
    public static String formatPropertyErrors(final MessageSource messageSource, final List<Pair<String, Pair<String, String>>> propertyToInvalidValueMessagePairs) {

        return propertyToInvalidValueMessagePairs.stream().map(pair -> formatPropertyError(messageSource, pair.getFirst(), pair.getSecond()
            .getFirst(), pair.getSecond().getSecond()))
            .collect(Collectors.joining("\n"));
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
        return messageSource.getMessage(fromEntityFieldValidationMessageKey(messageKeyDenoter), arguments, locale());
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
        return MessageFormat.format(resourceBundle.getString(fromEntityFieldValidationMessageKey(messageKeyDenoter)), arguments);
    }

    /**
     * Locale.
     *
     * @return the locale
     */
    public static Locale locale() {

        final Locale locale = LocaleContextHolder.getLocale();
        log.info("Request context locale: " + locale);
        return locale;
    }

    /**
     * Resource bundle.
     *
     * @param resourceBundleName the resource bundle name
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle(final String resourceBundleName) {
        return ResourceBundle.getBundle(resourceBundleName, locale());
    }

    /**
     * From entity field validation message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return the string
     */
    private static String fromEntityFieldValidationMessageKey(final String messageKeyDenoter) {

        if (!isEntityFieldValidationMessageKey(messageKeyDenoter)) {
            return messageKeyDenoter;
        }
        return messageKeyDenoter.substring(1, messageKeyDenoter.length() - 1);
    }


    /**
     * Checks if is entity field validation message key.
     *
     * @param messageKeyDenoter the message key denoter
     * @return true, if is entity field validation message key
     */
    private static boolean isEntityFieldValidationMessageKey(final String messageKeyDenoter) {

        return StringUtils.startsWith(messageKeyDenoter, ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX)
            && StringUtils.endsWith(messageKeyDenoter, ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX);
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

    /**
     * Format property error.
     *
     * @param messageSource the message source
     * @param propertyName the property name
     * @param invalidValue the invalid value
     * @param message the message
     * @return the error in the format: {@code propertyName}[{@code invalidValue}] => Error: {@code message}.
     */
    private static String formatPropertyError(final MessageSource messageSource, final String propertyName, final String invalidValue, final String message) {

        return propertyName + "[" + invalidValue + "] => Error: " + messageFromResource(messageSource, message);
    }

    /**
     * Message from resource.
     *
     * @param messageSource the message source
     * @param message the message
     * @return the message
     */
    private static String messageFromResource(final MessageSource messageSource, final String message) {
        return fromMessageSource(messageSource, message);
    }
}
