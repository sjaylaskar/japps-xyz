/*
 * Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.resources;

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
    private static final String ERROR_MESSAGE_PREFIX = "eventvenue.eventshow.message.error.";

    /** The message error not found for id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_EVENT_SHOW_ID = ERROR_MESSAGE_PREFIX + "notFound.for.eventShowId";

    /** The message error not found for event show id and seat numbers. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_EVENT_SHOW_ID_AND_SEAT_NUMBERS = ERROR_MESSAGE_PREFIX + "notFound.for.eventShowId.and.seatNumbers";

    /** The message error not found for ids. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_IDS = ERROR_MESSAGE_PREFIX + "notFound.forIds";

    /** The message error not found for event show id and reservation id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_FOR_EVENT_SHOW_ID_AND_RESERVATION_ID = ERROR_MESSAGE_PREFIX
        + "notFound.forEventShowId.and.forBookingReservationId";

    /** The message error not null booking reservation id. */
    public static final String MESSAGE_ERROR_NOT_NULL_BOOKING_RESERVATION_ID = ERROR_MESSAGE_PREFIX
        + "notNull.bookingReservationId";

    /** The message error not null event show id. */
    public static final String MESSAGE_ERROR_NOT_NULL_EVENT_SHOW_ID = ERROR_MESSAGE_PREFIX + "notNull.eventShowId";

    /** The message error not empty seat numbers. */
    public static final String MESSAGE_ERROR_NOT_EMPTY_SEAT_NUMBERS = ERROR_MESSAGE_PREFIX + "notEmpty.seatNumbers";

    /** The message error max seats per booking. */
    public static final String MESSAGE_ERROR_MAX_SEATS_PER_BOOKING = ERROR_MESSAGE_PREFIX + "max.seats.per.booking";

    /** The message error failure cancel. */
    public static final String MESSAGE_ERROR_FAILURE_CANCEL = ERROR_MESSAGE_PREFIX + "failure.cancel";

    /** The message error selected seats no longer available. */
    public static final String MESSAGE_ERROR_SELECTED_SEATS_NO_LONGER_AVAILABLE = ERROR_MESSAGE_PREFIX
        + "selected.seats.no.longer.available";

    /** The message error invalid combination of event show and seats. */
    public static final String MESSAGE_ERROR_INVALID_COMBINATION_OF_EVENT_SHOW_AND_SEATS = ERROR_MESSAGE_PREFIX
        + "invalid.combination.eventShow.and.seatNumbers";

    /** The message error invalid seat reservation status. */
    public static final String MESSAGE_ERROR_INVALID_SEAT_RESERVATION_STATUS = ERROR_MESSAGE_PREFIX
        + "invalid.seat.reservation.status";

    /** The message error not allowed booking show ended. */
    public static final String MESSAGE_ERROR_NOT_ALLOWED_BOOKING_SHOW_ENDED = ERROR_MESSAGE_PREFIX + "not.allowed.booking.show.ended";

    /** The message error not allowed cancellation show started. */
    public static final String MESSAGE_ERROR_NOT_ALLOWED_CANCELLATION_SHOW_STARTED = ERROR_MESSAGE_PREFIX + "not.allowed.cancellation.show.started";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {

        return MessageUtil.resourceBundle("module.eventvenue.eventshow.Messages");
    }
}
