/*
* Id: BookingServiceException.java 06-Mar-2022 6:12:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking;


/**
 * The booking service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class BookingServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2557145865886422564L;

    /**
     * Instantiates a new booking service exception.
     *
     * @param message the message
     */
    public BookingServiceException(final String message) {
        super(message);
    }
}
