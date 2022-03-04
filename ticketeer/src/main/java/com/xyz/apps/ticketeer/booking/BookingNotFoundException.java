/*
* Id: BookingNotFoundException.java 04-Mar-2022 1:16:59 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;


/**
 * The booking not found exception.
 */
public class BookingNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3861121481143099928L;

    /**
     * Instantiates a new booking not found exception.
     *
     * @param bookingId the booking id
     */
    public BookingNotFoundException(final Long bookingId) {
        super("Booking not found for id: " + bookingId);
    }
}
