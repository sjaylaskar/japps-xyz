/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.resources;

import java.util.ResourceBundle;

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

    /*
     * Model messages:
     */

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "event.message.error.";

    /** The error required message prefix. */
    private static final String ERROR_REQUIRED_MESSAGE_PREFIX = ERROR_MESSAGE_PREFIX + "required.event.";

    /** The message error required name. */
    public static final String MESSAGE_ERROR_REQUIRED_NAME = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "name}";

    /** The message error required id. */
    public static final String MESSAGE_ERROR_REQUIRED_ID = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "id}";

    /** The message error required type. */
    public static final String MESSAGE_ERROR_REQUIRED_TYPE = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "type}";

    /** The message error required language. */
    public static final String MESSAGE_ERROR_REQUIRED_LANGUAGE = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "language}";

    /** The message error required duration. */
    public static final String MESSAGE_ERROR_REQUIRED_DURATION = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "duration}";

    /** The message error required release date. */
    public static final String MESSAGE_ERROR_REQUIRED_RELEASE_DATE = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "releaseDate}";

    /** The message error required country name. */
    public static final String MESSAGE_ERROR_REQUIRED_COUNTRY_NAME = "{" + ERROR_REQUIRED_MESSAGE_PREFIX + "countryName}";

    /** The message error invalid duration. */
    public static final String MESSAGE_ERROR_INVALID_DURATION = "{" + ERROR_MESSAGE_PREFIX + "invalid.event.duration}";

    /*
     * Other messages:
     */

    /** The message error required event details. */
    public static final String MESSAGE_ERROR_REQUIRED_EVENT_DETAILS = ERROR_REQUIRED_MESSAGE_PREFIX + "details";

    /** The message error required event details list. */
    public static final String MESSAGE_ERROR_REQUIRED_EVENT_DETAILS_LIST = ERROR_REQUIRED_MESSAGE_PREFIX + "details.list";

    /** The message error required update event id. */
    public static final String MESSAGE_ERROR_NOT_NULL_EVENT_ID = ERROR_MESSAGE_PREFIX + "notNull.event.id";

    /** The message error not empty event id list. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_EVENT_ID_LIST = ERROR_MESSAGE_PREFIX + "notEmpty.event.id.list";

    /** The message error not null city id. */
    public static final String MESSAGE_ERROR_NOT_NULL_CITY_ID = ERROR_MESSAGE_PREFIX + "notNull.city.id";

    /** The message error no events found. */
    public static final String MESSAGE_ERROR_NO_EVENTS_FOUND = ERROR_MESSAGE_PREFIX + "notFound.events";

    /** The message error not found for id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_ID = ERROR_MESSAGE_PREFIX + "notFound.id";

    /** The message error not found for city. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_CITY = ERROR_MESSAGE_PREFIX + "notFound.city";

    /** The message error failure add. */
    public static final String MESSAGE_ERROR_FAILURE_ADD = ERROR_MESSAGE_PREFIX + "failure.add";

    /** The message error failure update. */
    public static final String MESSAGE_ERROR_FAILURE_UPDATE = ERROR_MESSAGE_PREFIX + "failure.update";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages.event.Messages");
    }
}
