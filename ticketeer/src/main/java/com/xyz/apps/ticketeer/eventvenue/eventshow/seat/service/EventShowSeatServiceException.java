/*
* Id: EventShowSeatServiceException.java 16-Mar-2022 6:54:32 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;


/**
 * The event show seat service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowSeatServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7029999969604913421L;

    /**
     * Instantiates a new event show seat service exception.
     *
     * @param message the message
     */
    public EventShowSeatServiceException(final String message) {
        super(message);
    }

}
