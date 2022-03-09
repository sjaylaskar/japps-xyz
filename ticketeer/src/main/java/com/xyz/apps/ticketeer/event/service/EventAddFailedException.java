/*
* Id: EventAddFailureException.java 03-Mar-2022 4:40:42 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.service;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;

import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDto;
import com.xyz.apps.ticketeer.util.CollectionUtil;

/**
 * The event add failed exception.
 */
public class EventAddFailedException extends EventServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7104455762990834368L;

    /**
     * Instantiates a new event add failed exception.
     *
     * @param eventDetailsDtos the event details dtos
     */
    public EventAddFailedException(@NotEmpty(message = "No events specified.") final Collection<EventDetailsDto> eventDetailsDtos) {
        super("Failed to add event(s): " + CollectionUtil.stringify(eventDetailsDtos));
    }
}