/*
* Id: PaymentNotFoundException.java 04-Mar-2022 1:25:45 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import com.xyz.apps.ticketeer.booking.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The payment not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class SuccessfulPaymentNotFoundForBookingException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -3469218342966086310L;

    /**
     * Instantiates a new successful payment not found for booking exception.
     *
     * @param bookingId the booking id
     */
    public SuccessfulPaymentNotFoundForBookingException(final Long bookingId) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_SUCCESSFUL_PAYMENT_FOR_BOOKING, bookingId);
    }

}
