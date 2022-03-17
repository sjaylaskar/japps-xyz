/*
* Id: EventShowSeatServiceException.java 16-Mar-2022 6:54:32 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NonLocalizedServiceException;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The event show seat service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowSeatServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7029999969604913421L;

    /**
     * Instantiates a new event show seat service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public EventShowSeatServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Non localized service exception.
     *
     * @param message the message
     * @return the non localized service exception
     */
    public static NonLocalizedServiceException nonLocalizedServiceException(final String message) {
        return NonLocalizedServiceException.of(message);
    }
}
