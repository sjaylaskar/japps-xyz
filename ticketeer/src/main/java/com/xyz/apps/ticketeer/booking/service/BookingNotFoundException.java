/*
* Id: BookingNotFoundException.java 04-Mar-2022 1:16:59 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;


/**
 * The booking not found exception.
 */
public class BookingNotFoundException extends BookingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3861121481143099928L;

    /**
     * Instantiates a new booking not found exception.
     *
     * @param message the message
     */
    public BookingNotFoundException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new booking not found exception.
     *
     * @param bookingId the booking id
     */
    public BookingNotFoundException(final Long bookingId) {
        super("Booking not found for id: " + bookingId);
    }

    /**
     * For username.
     *
     * @param username the username
     * @return the booking not found exception
     */
    public static BookingNotFoundException forUsername(final String username) {
        return new BookingNotFoundException("No booking(s) found for username: " + username);
    }

    /**
     * For username and id.
     *
     * @param username the username
     * @param bookingId the booking id
     * @return the booking not found exception
     */
    public static BookingNotFoundException forUsernameAndId(final String username, final Long bookingId) {
        return new BookingNotFoundException("Booking not found for user: " + username + " and booking: " + bookingId);
    }
}
