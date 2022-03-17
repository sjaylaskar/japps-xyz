/*
* Id: EventVenueNotFoundException.java 06-Mar-2022 4:39:46 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The event venue not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventVenueNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -4218140171803098203L;

    public EventVenueNotFoundException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Instantiates a new event venue not found exception.
     *
     * @param id the id
     */
    public EventVenueNotFoundException(final Long id) {
        this(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_ID, id);
    }

    /**
     * For city id.
     *
     * @param cityId the city id
     * @return the event venue not found exception
     */
    public static EventVenueNotFoundException forCityId(final Long cityId) {

        return new EventVenueNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_CITY_ID, cityId);
    }
}
