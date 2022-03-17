/*
* Id: InvalidEventShowSeatsException.java 06-Mar-2022 2:19:55 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NonLocalizedServiceException;
import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.util.CollectionUtil;

/**
 * The event show seats not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EventShowSeatsNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -5121506571197848879L;

    /**
     * Non localized not found exception.
     *
     * @param messsage the messsage
     * @return the not found exception
     */
    public static NonLocalizedServiceException nonLocalizedNotFoundException(final String messsage) {
        return NonLocalizedServiceException.of(messsage, HTTP_STATUS);
    }

    /**
     * Auditorium not found exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public EventShowSeatsNotFoundException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * Instantiates a new event show seats not found exception.
     *
     * @param eventShowSeatIds the event show seat ids
     */
    public EventShowSeatsNotFoundException(final Collection<Long> eventShowSeatIds) {
        this(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_IDS, CollectionUtil.stringify(eventShowSeatIds));
    }

    /**
     * For event show and row name.
     *
     * @param eventShowId the event show id
     * @param rowName the row name
     * @return the event show seats not found exception
     */
    public static NonLocalizedServiceException forEventShowAndRowName(final Long eventShowId, final String rowName) {
        return nonLocalizedNotFoundException("Event show seats not found for event show id: " + eventShowId + " and seat row name: " + rowName);
    }

    /**
     * For event show and seat number.
     *
     * @param eventShowId the event show id
     * @param seatNumber the seat number
     * @return the event show seats not found exception
     */
    public static NonLocalizedServiceException forEventShowAndSeatNumber(final Long eventShowId, final String seatNumber) {
        return nonLocalizedNotFoundException("Event show seats not found for event show id: " + eventShowId + " and seat number: " + seatNumber);
    }

    /**
     * For event show.
     *
     * @param eventShowId the event show id
     * @return the event show seats not found exception
     */
    public static EventShowSeatsNotFoundException forEventShow(@NotNull(message = "The event show id cannot be null.") final Long eventShowId) {

        return new EventShowSeatsNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_ID, eventShowId);
    }

}
