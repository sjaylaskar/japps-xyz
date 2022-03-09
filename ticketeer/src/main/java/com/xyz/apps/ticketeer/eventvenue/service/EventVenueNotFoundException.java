/*
* Id: EventVenueNotFoundException.java 06-Mar-2022 4:39:46 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;


/**
 * The event venue not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventVenueNotFoundException extends EventVenueServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -4218140171803098203L;

    /**
     * Instantiates a new event venue not found exception.
     *
     * @param id the id
     */
    public EventVenueNotFoundException(final Long id) {
        super("Event venue not found for id: " + id);
    }
}
