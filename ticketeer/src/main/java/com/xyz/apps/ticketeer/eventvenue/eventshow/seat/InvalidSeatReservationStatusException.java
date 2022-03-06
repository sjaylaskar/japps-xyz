/*
* Id: InvalidSeatReservationStatusException.java 06-Mar-2022 11:48:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import com.xyz.apps.ticketeer.eventvenue.eventshow.EventShowServiceException;

/**
 * The InvalidSeatReservationStatusException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidSeatReservationStatusException extends EventShowServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -5557778016124471204L;


    /**
     * Instantiates a new invalid seat reservation status exception.
     *
     * @param status the status
     */
    public InvalidSeatReservationStatusException(final String status) {
        super("Invalid seat reservation status: " + status);
    }
}
