/*
* Id: EventNotFoundException.java 06-Mar-2022 11:03:44 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.service;

import java.util.Collection;

import com.xyz.apps.ticketeer.util.CollectionUtil;

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

    /**
     * Instantiates a new event not found exception.
     *
     * @param eventIds the event ids
     */
    public EventNotFoundException(final Collection<Long> eventIds) {
        super("Events not found for ids: " + CollectionUtil.stringify(eventIds));
    }

    /**
     * Instantiates a new event not found exception.
     *
     * @param message the message
     */
    public EventNotFoundException(final String message) {

        super(message);
    }

    /**
     * For no events for city.
     *
     * @param cityId the city id
     * @return the event not found exception
     */
    public static EventNotFoundException forCityId(final Long cityId) {
        return new EventNotFoundException("No events found for city: " + cityId);
    }

}
