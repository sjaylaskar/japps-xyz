/*
* Id: BookingServiceException.java 06-Mar-2022 6:12:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import com.xyz.apps.ticketeer.booking.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NonLocalizedServiceException;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The booking service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class BookingServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -2557145865886422564L;

    /**
     * Non localized service exception.
     * To be removed in future versions.
     *
     * @param message the message
     * @return the service exception
     */
    @Deprecated(forRemoval = true)
    public static NonLocalizedServiceException nonLocalizedServiceException(final String message) {
        return NonLocalizedServiceException.of(message);
    }

    /**
     * Instantiates a new booking service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public BookingServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
