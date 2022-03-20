/*
 * Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.resources;

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
    private static final String ERROR_MESSAGE_PREFIX = "booking.message.error.";

    /*
     * Model messages:
     */

    /*
     * Other messages:
     */

    /** The message error booking expired. */
    public static final String MESSAGE_ERROR_BOOKING_EXPIRED = ERROR_MESSAGE_PREFIX + "booking.expired";

    /** The message error not found for id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_ID = ERROR_MESSAGE_PREFIX + "notFound.forId";

    /** The message error not found for username. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME = ERROR_MESSAGE_PREFIX + "notFound.forUsername";

    /** The message error not found for username and id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME_AND_ID = ERROR_MESSAGE_PREFIX + "notFound.forUsernameAndId";

    /** The message error not found successful payment for booking. */
    public static final String MESSAGE_ERROR_NOT_FOUND_SUCCESSFUL_PAYMENT_FOR_BOOKING = ERROR_MESSAGE_PREFIX
        + "notFound.successfulPayment.forBooking";

    /** The message error invalid booking status. */
    public static final String MESSAGE_ERROR_INVALID_BOOKING_STATUS = ERROR_MESSAGE_PREFIX + "invalid.booking.status";

    /** The message error unavailable selected seats. */
    public static final String MESSAGE_ERROR_UNAVAILABLE_SELECTED_SEATS = ERROR_MESSAGE_PREFIX + "unavailable.selectedSeats";

    /** The message error required id. */
    public static final String MESSAGE_ERROR_REQUIRED_ID = ERROR_MESSAGE_PREFIX + "required.id";

    /** The message error invalid request. */
    public static final String MESSAGE_ERROR_INVALID_REQUEST = ERROR_MESSAGE_PREFIX + "invalid.booking.request";

    /** The message error invalid seats for reservation. */
    public static final String MESSAGE_ERROR_INVALID_SEATS_FOR_RESERVATION = ERROR_MESSAGE_PREFIX + "invalid.seats.for.reservation";

    /** The message error invalid event show id. */
    public static final String MESSAGE_ERROR_INVALID_EVENT_SHOW_ID = ERROR_MESSAGE_PREFIX + "invalid.event.show.id";

    /** The message error minimum one seat. */
    public static final String MESSAGE_ERROR_MINIMUM_ONE_SEAT = ERROR_MESSAGE_PREFIX + "required.minimum.one.seat";

    /** The message error max seats per booking. */
    public static final String MESSAGE_ERROR_MAX_SEATS_PER_BOOKING = ERROR_MESSAGE_PREFIX + "max.seats.allowed.per.booking";

    /** The message error invalid seats selected. */
    public static final String MESSAGE_ERROR_INVALID_SEATS_SELECTED = ERROR_MESSAGE_PREFIX + "invalid.seats.selected";

    /** The message error invalid booking for confirmation. */
    public static final String MESSAGE_ERROR_INVALID_BOOKING_FOR_CONFIRMATION = ERROR_MESSAGE_PREFIX + "invalid.booking.for.confirmation";

    /** The message error not found confirmed booking for username and id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_CONFIRMED_BOOKING_FOR_USERNAME_AND_ID = ERROR_MESSAGE_PREFIX + "notFound.confirmed.booking.for.username.and.id";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {

        return MessageUtil.resourceBundle("module.booking.Messages");
    }
}
