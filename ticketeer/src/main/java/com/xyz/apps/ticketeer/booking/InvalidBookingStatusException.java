/*
* Id: InvalidBookingStatusException.java 07-Mar-2022 12:31:49 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;


/**
 * The InvalidBookingStatusException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidBookingStatusException extends BookingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2310396073533668142L;

    /**
     * Instantiates a new invalid booking status exception.
     *
     * @param bookingStatus the booking status
     */
    public InvalidBookingStatusException(final String bookingStatus) {
        super("Invalid booking status: " + bookingStatus);
    }
}
