/*
* Id: EventNotFoundException.java 03-Mar-2022 11:08:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;

/**
 * The event show not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowNotFoundException extends EventShowServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 2864961314454364365L;

    /**
     * Instantiates a new event not found exception.
     *
     * @param id the id
     */
    public EventShowNotFoundException(final Long id) {
        super("Event show not found for id: " + id);
    }

    public EventShowNotFoundException(final String message) {
        super(message);
    }

    /**
     * For city id.
     *
     * @param cityId the city id
     * @return the event show not found exception
     */
    public static EventShowNotFoundException forCityId(final Long cityId) {
        return new EventShowNotFoundException("No event shows for city: " + cityId);
    }
}
