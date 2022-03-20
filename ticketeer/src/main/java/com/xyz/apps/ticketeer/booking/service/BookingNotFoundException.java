/*
* Id: BookingNotFoundException.java 04-Mar-2022 1:16:59 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import com.xyz.apps.ticketeer.booking.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The booking not found exception.
 */
public class BookingNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3861121481143099928L;

    /**
     * Instantiates a new booking not found exception.
     *
     * @param message the message
     */
    public BookingNotFoundException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Instantiates a new booking not found exception.
     *
     * @param bookingId the booking id
     */
    public BookingNotFoundException(final Long bookingId) {
        this(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_ID, bookingId);
    }

    /**
     * For username.
     *
     * @param username the username
     * @return the booking not found exception
     */
    public static BookingNotFoundException forUsername(final String username) {
        return new BookingNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME, username);
    }

    /**
     * For username and id.
     *
     * @param username the username
     * @param bookingId the booking id
     * @return the booking not found exception
     */
    public static BookingNotFoundException forUsernameAndId(final String username, final Long bookingId) {
        return new BookingNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_USERNAME_AND_ID, username, bookingId);
    }

    /**
     * For username and id and confirmed status.
     *
     * @param username the username
     * @param bookingId the booking id
     * @return the booking not found exception
     */
    public static BookingNotFoundException forUsernameAndIdAndConfirmedStatus(final String username, final Long bookingId) {
        return new BookingNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_CONFIRMED_BOOKING_FOR_USERNAME_AND_ID, username, bookingId);
    }
}
