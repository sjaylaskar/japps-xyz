/*
* Id: AuditoriumSeatServiceException.java 15-Mar-2022 9:07:22 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The auditorium seat service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumSeatServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1934423546724271057L;

    /**
     * Instantiates a new auditorium seat service exception.
     *
     * @param message the message
     */
    public AuditoriumSeatServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
