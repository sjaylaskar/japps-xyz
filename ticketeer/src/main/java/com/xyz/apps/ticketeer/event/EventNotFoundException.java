/*
* Id: EventNotFoundException.java 06-Mar-2022 11:03:44 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;


/**
 * The event not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventNotFoundException extends EventServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 116556244973119017L;

    /**
     * Instantiates a new event not found exception.
     *
     * @param id the id
     */
    public EventNotFoundException(final Long id) {
        super("Event not found for id: " + id);
    }

}