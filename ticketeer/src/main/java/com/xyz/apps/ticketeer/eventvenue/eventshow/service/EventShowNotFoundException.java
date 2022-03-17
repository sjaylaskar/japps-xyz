/*
* Id: EventNotFoundException.java 03-Mar-2022 11:08:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NonLocalizedServiceException;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The event show not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 2864961314454364365L;

    public static NonLocalizedServiceException nonLocalizedServiceException(final String message) {
        return NonLocalizedServiceException.of(message, HTTP_STATUS);
    }

    /**
     * Instantiates a new event not found exception.
     *
     * @param id the id
     */
    public static NonLocalizedServiceException forId(final Long id) {
        return nonLocalizedServiceException("Event show not found for id: " + id);
    }

    public EventShowNotFoundException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * For city id.
     *
     * @param cityId the city id
     * @return the event show not found exception
     */
    public static NonLocalizedServiceException forCityId(final Long cityId) {
        return nonLocalizedServiceException("No event shows for city: " + cityId);
    }
}
