/*
* Id: AuditoriumServiceException.java 15-Mar-2022 3:14:24 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The auditorium service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -4687969398830792939L;

    /**
     * Instantiates a new auditorium service exception.
     *
     * @param message the message
     */
    public AuditoriumServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
