/*
* Id: InvalidEventShowSeatsException.java 06-Mar-2022 2:19:55 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.util.Collection;

import com.xyz.apps.ticketeer.util.CollectionUtil;

/**
 * The event show seats not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowSeatsNotFoundException extends EventShowServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -5121506571197848879L;

    /**
     * Instantiates a new event show seats not found exception.
     *
     * @param eventShowSeatIds the event show seat ids
     */
    public EventShowSeatsNotFoundException(final Collection<Long> eventShowSeatIds) {
        super("Event show seats not found for ids: " + CollectionUtil.stringify(eventShowSeatIds));
    }

}
