/*
* Id: InvalidEventShowSeatsException.java 06-Mar-2022 2:19:55 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowServiceException;
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

    /**
     * Instantiates a new event show seats not found exception.
     *
     * @param message the message
     */
    public EventShowSeatsNotFoundException(final String message) {
        super(message);
    }

    /**
     * For event show and row name.
     *
     * @param eventShowId the event show id
     * @param rowName the row name
     * @return the event show seats not found exception
     */
    public static EventShowSeatsNotFoundException forEventShowAndRowName(final Long eventShowId, final String rowName) {
        return new EventShowSeatsNotFoundException("Event show seats not found for event show id: " + eventShowId + " and seat row name: " + rowName);
    }

    /**
     * For event show and seat number.
     *
     * @param eventShowId the event show id
     * @param seatNumber the seat number
     * @return the event show seats not found exception
     */
    public static EventShowSeatsNotFoundException forEventShowAndSeatNumber(final Long eventShowId, final String seatNumber) {
        return new EventShowSeatsNotFoundException("Event show seats not found for event show id: " + eventShowId + " and seat number: " + seatNumber);
    }

    /**
     * For event show.
     *
     * @param eventShowId the event show id
     * @return the event show seats not found exception
     */
    public static EventShowSeatsNotFoundException forEventShow(@NotNull(message = "The event show id cannot be null.") final Long eventShowId) {

        return new EventShowSeatsNotFoundException("Event show seats not found for event show id: " + eventShowId);
    }

}
