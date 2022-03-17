/*
* Id: EventShowSeatReservationServiceException.java 16-Mar-2022 3:02:51 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.ServiceException;

/**
 * The EventShowSeatReservationServiceException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowSeatReservationServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 2045672256495594018L;

    /**
     * Instantiates a new event show seat reservation service exception.
     *
     * @param message the message
     */
    public EventShowSeatReservationServiceException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

}
