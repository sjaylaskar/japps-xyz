/*
* Id: EventVenueServiceException.java 06-Mar-2022 10:35:36 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;


/**
 * The EventVenueServiceException.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventVenueServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = -4756268150442065605L;

    /**
     * Instantiates a new event venue service exception.
     *
     * @param message the message
     */
    public EventVenueServiceException(final String message) {
        super(message);
    }
}
