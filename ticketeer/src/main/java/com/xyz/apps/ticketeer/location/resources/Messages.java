/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.resources;

import java.util.ResourceBundle;

import com.xyz.apps.ticketeer.util.MessageUtil;

/**
 * The messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Messages {

    /**
     * Instantiates a new messages.
     */
    private Messages() {

    }

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "location.message.error.";

    /*
     * Model messages:
     */

    /** The message error required country code. */
    public static final String MESSAGE_ERROR_REQUIRED_COUNTRY_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.country.code" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required country name. */
    public static final String MESSAGE_ERROR_REQUIRED_COUNTRY_NAME = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.country.name" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error invalid country code. */
    public static final String MESSAGE_ERROR_INVALID_COUNTRY_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "invalid.country.code" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required city code. */
    public static final String MESSAGE_ERROR_REQUIRED_CITY_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.city.code" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required city name. */
    public static final String MESSAGE_ERROR_REQUIRED_CITY_NAME = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.city.name" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error required city country. */
    public static final String MESSAGE_ERROR_REQUIRED_CITY_COUNTRY = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "required.city.country" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /** The message error invalid city code. */
    public static final String MESSAGE_ERROR_INVALID_CITY_CODE = MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_PREFIX + ERROR_MESSAGE_PREFIX + "invalid.city.code" + MessageUtil.ENTITY_FIELD_VALIDATION_MESSAGE_KEY_SUFFIX;

    /*
     * Other messages:
     */

    /** The message error not null country. */
    public static final String MESSAGE_ERROR_NOT_NULL_COUNTRY = ERROR_MESSAGE_PREFIX + "notNull.country";

    /** The message error not null country list. */
    public static final String MESSAGE_ERROR_NOT_NULL_COUNTRY_LIST = ERROR_MESSAGE_PREFIX + "notNull.country.list";

    /** The message error not null country id. */
    public static final String MESSAGE_ERROR_NOT_NULL_COUNTRY_ID = ERROR_MESSAGE_PREFIX + "notNull.country.id";

    /** The message error not blank country code. */
    public static final String MESSAGE_ERROR_NOT_BLANK_COUNTRY_CODE = ERROR_MESSAGE_PREFIX + "notBlank.country.code";

    /** The message error not null city. */
    public static final String MESSAGE_ERROR_NOT_NULL_CITY = ERROR_MESSAGE_PREFIX + "notNull.city";

    /** The message error not null city list. */
    public static final String MESSAGE_ERROR_NOT_NULL_CITY_LIST = ERROR_MESSAGE_PREFIX + "notNull.city.list";

    /** The message error not null city id. */
    public static final String MESSAGE_ERROR_NOT_NULL_CITY_ID = ERROR_MESSAGE_PREFIX + "notNull.city.id";

    /** The message error not null city code. */
    public static final String MESSAGE_ERROR_NOT_NULL_CITY_CODE = ERROR_MESSAGE_PREFIX + "notBlank.city.code";

    /** The message error not found country id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_COUNTRY_ID = ERROR_MESSAGE_PREFIX + "notFound.country.id";

    /** The message error not found country code. */
    public static final String MESSAGE_ERROR_NOT_FOUND_COUNTRY_CODE = ERROR_MESSAGE_PREFIX + "notFound.country.code";

    /** The message error not found country name. */
    public static final String MESSAGE_ERROR_NOT_FOUND_COUNTRY_NAME = ERROR_MESSAGE_PREFIX + "notFound.country.name";

    /** The message error not found countries. */
    public static final String MESSAGE_ERROR_NOT_FOUND_COUNTRIES = ERROR_MESSAGE_PREFIX + "notFound.countries";

    /** The message error not found city id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CITY_ID = ERROR_MESSAGE_PREFIX + "notFound.city.id";

    /** The message error not found city code. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CITY_CODE = ERROR_MESSAGE_PREFIX + "notFound.city.code";

    /** The message error not found city name. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CITY_NAME = ERROR_MESSAGE_PREFIX + "notFound.city.name";

    /** The message error not found cities. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CITIES = ERROR_MESSAGE_PREFIX + "notFound.cities";

    /** The message error already exists country code. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_COUNTRY_CODE = ERROR_MESSAGE_PREFIX + "alreadyExists.country.code";

    /** The message error already exists city code. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_CITY_CODE = ERROR_MESSAGE_PREFIX + "alreadyExists.city.code";

    /** The message error failed country add. */
    public static final String MESSAGE_ERROR_FAILED_COUNTRY_ADD =  ERROR_MESSAGE_PREFIX + "failed.country.add";

    /** The message error failed country update. */
    public static final String MESSAGE_ERROR_FAILED_COUNTRY_UPDATE =  ERROR_MESSAGE_PREFIX + "failed.country.update";

    /** The message error failed city add. */
    public static final String MESSAGE_ERROR_FAILED_CITY_ADD =  ERROR_MESSAGE_PREFIX + "failed.city.add";

    /** The message error failed city update. */
    public static final String MESSAGE_ERROR_FAILED_CITY_UPDATE =  ERROR_MESSAGE_PREFIX + "failed.city.update";

    /** The message error unique country codes. */
    public static final String MESSAGE_ERROR_UNIQUE_COUNTRY_CODES = ERROR_MESSAGE_PREFIX + "unique.country.codes";

    /** The message error unique city codes. */
    public static final String MESSAGE_ERROR_UNIQUE_CITY_CODES = ERROR_MESSAGE_PREFIX + "unique.city.codes";

    /** The message error failed countries add. */
    public static final String MESSAGE_ERROR_FAILED_COUNTRIES_ADD = ERROR_MESSAGE_PREFIX + "failed.countries.add";

    /** The message error failed cities add. */
    public static final String MESSAGE_ERROR_FAILED_CITIES_ADD = ERROR_MESSAGE_PREFIX + "failed.cities.add";

    /** The message error not found city for country. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CITY_FOR_COUNTRY = ERROR_MESSAGE_PREFIX + "notFound.city.for.country";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return MessageUtil.resourceBundle("module.location.Messages");
    }
}
