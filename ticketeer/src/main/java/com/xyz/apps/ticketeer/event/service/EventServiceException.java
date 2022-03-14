/*
* Id: EventServiceException.java 06-Mar-2022 11:04:43 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.service;

import com.xyz.apps.ticketeer.event.resources.Messages;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The EventServiceException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 1212503819964034797L;

    /**
     * Instantiates a new event service exception.
     *
     * @param message the message
     */
    public EventServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
