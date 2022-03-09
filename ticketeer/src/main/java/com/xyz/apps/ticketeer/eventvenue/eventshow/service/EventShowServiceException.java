/*
* Id: EventShowServiceException.java 06-Mar-2022 2:17:59 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;


/**
 * The event show service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 6709155232644583326L;

    /**
     * Instantiates a new event show service exception.
     *
     * @param message the message
     */
    public EventShowServiceException(final String message) {
        super(message);
    }
}
