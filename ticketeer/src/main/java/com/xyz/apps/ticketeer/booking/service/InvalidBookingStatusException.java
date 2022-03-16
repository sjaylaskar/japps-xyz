/*
* Id: InvalidBookingStatusException.java 07-Mar-2022 12:31:49 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import com.xyz.apps.ticketeer.booking.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The invalid booking status exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidBookingStatusException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2310396073533668142L;

    /**
     * Instantiates a new invalid booking status exception.
     *
     * @param bookingStatus the booking status
     */
    public InvalidBookingStatusException(final String bookingStatus) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_INVALID_BOOKING_STATUS, bookingStatus);
    }
}
