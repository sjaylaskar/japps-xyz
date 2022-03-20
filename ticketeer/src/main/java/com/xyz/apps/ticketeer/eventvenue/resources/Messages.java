/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.resources;

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
    private static final String ERROR_MESSAGE_PREFIX = "eventvenue.message.error.";

    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_ID = ERROR_MESSAGE_PREFIX + "notFound.forId";

    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_CITY_ID = ERROR_MESSAGE_PREFIX + "notFound.forCityId";

    public static final String MESSAGE_ERROR_INVALID_CITY = ERROR_MESSAGE_PREFIX + "invalid.cityId";

    public static final String MESSAGE_ERROR_INVALID_EVENT = ERROR_MESSAGE_PREFIX + "invalid.eventId";

    public static final String MESSAGE_ERROR_INVALID_EVENT_VENUE_ID = ERROR_MESSAGE_PREFIX + "invalid.eventVenueId";

    public static final String MESSAGE_ERROR_FAILURE_ADD_EVENT_VENUE = ERROR_MESSAGE_PREFIX + "failure.add.event.venue";

    public static final String MESSAGE_ERROR_NOT_NULL_EVENT_VENUE_ID = ERROR_MESSAGE_PREFIX + "notNull.eventVenueId";

    public static final String MESSAGE_ERROR_FAILURE_UPDATE_EVENT_VENUE = ERROR_MESSAGE_PREFIX + "failure.update.event.venue";

    public static final String MESSAGE_ERROR_FAILURE_ADD_AUDITORIUM = ERROR_MESSAGE_PREFIX + "failure.add.auditorium";

    /** The message error already exists auditorium. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_AUDITORIUM_WITH_NAME_FOR_EVENT_VENUE_ID = ERROR_MESSAGE_PREFIX + "alreadyExists.auditorium.with.name.forEventVenueId";

    public static final String MESSAGE_ERROR_ALREADY_EXISTS_AUDITORIUM_NAMES_FOR_EVENT_VENUE_ID = ERROR_MESSAGE_PREFIX +  "alreadyExist.auditorium.with.names.and.event.venue.id";

    public static final String MESSAGE_ERROR_NOT_EMPTY_AUDITORIUM_LIST = ERROR_MESSAGE_PREFIX + "notEmpty.auditorium.list";



    public static final String MESSAGE_ERROR_NOT_BLANK_AUDITORIUM_NAME = ERROR_MESSAGE_PREFIX +  "notBlank.auditorium.name";

    public static final String MESSAGE_ERROR_UNIQUE_AUDITORIUM_NAMES = ERROR_MESSAGE_PREFIX +  "unique.auditorium.names";

    /** The message error not found auditorium for id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_ID = ERROR_MESSAGE_PREFIX + "notFound.auditorium.forId";

    /** The message error not found auditorium for event venue and id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE_AND_ID = ERROR_MESSAGE_PREFIX + "notFound.auditorium.for.event.venue.and.id";

    /** The message error not found auditorium for event venue and name. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE_AND_NAME = ERROR_MESSAGE_PREFIX + "notFound.auditorium.for.event.venue.and.name";

    /** The message error not found auditorium for event venue. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE = ERROR_MESSAGE_PREFIX + "notFound.auditorium.for.event.venue.id";

    /** The message error not found auditorium seats for auditorium id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_AUDITORIUM_ID = ERROR_MESSAGE_PREFIX + "notFound.auditorium.seats.for.auditorium.id";

    /** The message error not found auditorium seats for event venue id auditorium and row name. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AUDITORIUM_AND_ROW_NAME = ERROR_MESSAGE_PREFIX + "notFound.auditorium.seats.for.eventVenueId.auditorium.and.seatRowName";

    /** The message error not found auditorium seats for event venue id auditorium row name and seat number. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AUDITORIUM_ROW_NAME_AND_SEAT_NUMBER  = ERROR_MESSAGE_PREFIX + "notFound.auditorium.seats.for.eventVenueId.auditorium.seatRowName.and.seatNumber";

    /** The message error not found auditorium seats for event venue id and auditorium. */
    public static final String MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AND_AUDITORIUM = ERROR_MESSAGE_PREFIX + "notFound.auditorium.seats.for.eventVenueId.and.auditorium";

    public static final String MESSAGE_ERROR_FAILURE_ADD_AUDITORIUM_SEATS = ERROR_MESSAGE_PREFIX + "failure.add.auditorium.seats";

    public static final String MESSAGE_ERROR_NOT_EMPTY_AUDITORIUM_SEAT_ROWS = ERROR_MESSAGE_PREFIX + "notEmpty.auditorium.seat.rows";

    public static final String MESSAGE_ERROR_NOT_EMPTY_AUDITORIUM_SEAT_ROWS_AND_UNIQUE = ERROR_MESSAGE_PREFIX + "notEmpty.auditorium.seat.rows.and.unique";

    public static final String MESSAGE_ERROR_AUDITORIUM_SEAT_MIN_ROWS_ONE = ERROR_MESSAGE_PREFIX + "auditorium.seat.min.rows.one";

    public static final String MESSAGE_ERROR_NOT_NULL_AUDITORIUM_NAME = ERROR_MESSAGE_PREFIX + "not.null.auditorium.name";


    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return MessageUtil.resourceBundle("module.eventvenue.Messages");
    }
}
