/*
* Id: InvalidSeatReservationStatusException.java 06-Mar-2022 11:48:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The InvalidSeatReservationStatusException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidSeatReservationStatusException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -5557778016124471204L;


    /**
     * Instantiates a new invalid seat reservation status exception.
     *
     * @param status the status
     */
    public InvalidSeatReservationStatusException(final String status) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_INVALID_SEAT_RESERVATION_STATUS, status);
    }
}
