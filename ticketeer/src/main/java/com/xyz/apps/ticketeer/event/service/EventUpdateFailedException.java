/*
* Id: EventUpdateFailedException.java 03-Mar-2022 4:40:42 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.service;

import java.util.Collection;

import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDto;
import com.xyz.apps.ticketeer.event.resources.Messages;
import com.xyz.apps.ticketeer.util.CollectionUtil;

/**
 * The event update failed exception.
 */
public class EventUpdateFailedException extends EventServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7104455762990834368L;

    /**
     * Instantiates a new event update failed exception.
     *
     * @param eventDetailsDtos the event details dtos
     */
    public EventUpdateFailedException(final Collection<EventDetailsDto> eventDetailsDtos) {
        super(Messages.MESSAGE_ERROR_FAILURE_UPDATE, CollectionUtil.stringify(eventDetailsDtos));
    }
}
