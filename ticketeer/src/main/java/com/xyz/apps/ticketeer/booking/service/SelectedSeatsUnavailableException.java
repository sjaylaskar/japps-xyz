/*
* Id: SelectedSeatsUnavailableException.java 03-Mar-2022 3:27:16 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import com.xyz.apps.ticketeer.booking.resources.Messages;

/**
 * The selected seats unavailable exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class SelectedSeatsUnavailableException extends BookingServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1004173545467542294L;

    /**
     * Instantiates a new selected seats unavailable exception.
     */
    public SelectedSeatsUnavailableException() {
        super(Messages.MESSAGE_ERROR_UNAVAILABLE_SELECTED_SEATS);
    }
}
