/*
 * Id: EventNotFoundException.java 06-Mar-2022 11:03:44 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event.service;

import java.util.Collection;

import com.xyz.apps.ticketeer.event.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.util.CollectionUtil;


/**
 * The event not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 116556244973119017L;

    /**
     * Instantiates a new event not found exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public EventNotFoundException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * None found.
     *
     * @return the event not found exception
     */
    public static EventNotFoundException noneFound() {
        return new EventNotFoundException(Messages.MESSAGE_ERROR_NO_EVENTS_FOUND);
    }

    /**
     * Instantiates a new event not found exception.
     *
     * @param id the id
     * @return the event not found exception
     */
    public static EventNotFoundException forId(final Long id) {

        return new EventNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_ID, id);
    }

    /**
     * Instantiates a new event not found exception.
     *
     * @param ids the ids
     * @return the event not found exception
     */
    public static EventNotFoundException forIds(final Collection<Long> ids) {

        return new EventNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_ID, CollectionUtil.stringify(ids));
    }

    /**
     * For no events for city.
     *
     * @param cityId the city id
     * @return the event not found exception
     */
    public static EventNotFoundException forCityId(final Long cityId) {

        return new EventNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_CITY, cityId);
    }

}
