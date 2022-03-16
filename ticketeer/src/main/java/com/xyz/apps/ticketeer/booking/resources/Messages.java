/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.resources;

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

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "booking.message.error.";

    /*
     * Model messages:
     */

    /** The message error required username. */
    public static final String MESSAGE_ERROR_REQUIRED_USERNAME = "{" + ERROR_MESSAGE_PREFIX + "required.username}";

    /** The message error required password. */
    public static final String MESSAGE_ERROR_REQUIRED_PASSWORD = "{" + ERROR_MESSAGE_PREFIX + "required.password}";

    /** The message error required phone number. */
    public static final String MESSAGE_ERROR_REQUIRED_PHONE_NUMBER = "{" + ERROR_MESSAGE_PREFIX + "required.phoneNumber}";

    /** The message error required email. */
    public static final String MESSAGE_ERROR_REQUIRED_EMAIL = "{" + ERROR_MESSAGE_PREFIX + "required.email}";

    /** The message error required first name. */
    public static final String MESSAGE_ERROR_REQUIRED_FIRST_NAME = "{" + ERROR_MESSAGE_PREFIX + "required.firstname}";

    /** The message error invalid username. */
    public static final String MESSAGE_ERROR_INVALID_USERNAME = "{" + ERROR_MESSAGE_PREFIX + "invalid.username}";

    /** The message error invalid password. */
    public static final String MESSAGE_ERROR_INVALID_PASSWORD = "{" + ERROR_MESSAGE_PREFIX + "invalid.password}";

    /** The message error invalid phone number. */
    public static final String MESSAGE_ERROR_INVALID_PHONE_NUMBER = "{" + ERROR_MESSAGE_PREFIX + "invalid.phoneNumber}";

    /** The message error invalid email. */
    public static final String MESSAGE_ERROR_INVALID_EMAIL = "{" + ERROR_MESSAGE_PREFIX + "invalid.email}";

    /*
     * Other messages:
     */

    /** The message error booking expired. */
    public static final String MESSAGE_ERROR_BOOKING_EXPIRED = "booking.expired";

    /** The message error not found for id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_ID = "notFound.forId";

    /** The message error not found for username. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME = "notFound.forUsername";

    /** The message error not found for username and id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME_AND_ID = "notFound.forUsernameAndId";

    /** The message error not found successful payment for booking. */
    public static final String MESSAGE_ERROR_NOT_FOUND_SUCCESSFUL_PAYMENT_FOR_BOOKING = "notFound.successfulPayment.forBooking";

    /** The message error invalid booking status. */
    public static final String MESSAGE_ERROR_INVALID_BOOKING_STATUS = "invalid.booking.status";

    /** The message error unavailable selected seats. */
    public static final String MESSAGE_ERROR_UNAVAILABLE_SELECTED_SEATS = "unavailable.selectedSeats";

    /** The message error required id. */
    public static final String MESSAGE_ERROR_REQUIRED_ID = "required.id";

    /** The message error invalid request. */
    public static final String MESSAGE_ERROR_INVALID_REQUEST = "invalid.booking.request";

    /** The message error invalid seats for reservation. */
    public static final String MESSAGE_ERROR_INVALID_SEATS_FOR_RESERVATION = "invalid.seats.for.reservation";

    /** The message error invalid event show id. */
    public static final String MESSAGE_ERROR_INVALID_EVENT_SHOW_ID = "invalid.event.show.id";

    /** The message error minimum one seat. */
    public static final String MESSAGE_ERROR_MINIMUM_ONE_SEAT = "required.minimum.one.seat";

    /** The message error max seats per booking. */
    public static final String MESSAGE_ERROR_MAX_SEATS_PER_BOOKING = "max.seats.allowed.per.booking";

    /** The message error invalid seats selected. */
    public static final String MESSAGE_ERROR_INVALID_SEATS_SELECTED = "invalid.seats.selected";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("module.booking.Messages");
    }
}
