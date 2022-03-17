/*
* Id: EventShowServiceException.java 06-Mar-2022 2:17:59 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NonLocalizedServiceException;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The event show service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 6709155232644583326L;

    /**
     * Instantiates a new event show service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public EventShowServiceException(final String messageKey, final Object ...messageArguments) {
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
