/*
 * Id: AuditoriumSeatNotFoundException.java 16-Mar-2022 12:10:01 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;


/**
 * The auditorium seat not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumSeatNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 8246173196749690709L;

    /**
     * Instantiates a new auditorium seat not found exception.
     *
     * @param message the message
     */
    public AuditoriumSeatNotFoundException(final String messageKey, final Object... messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * For event venue and auditorium and row name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @return the auditorium seat not found exception
     */
    public static AuditoriumSeatNotFoundException forEventVenueAndAuditoriumAndRowName(final Long eventVenueId,
            final String auditoriumName, final String seatRowName) {

        return new AuditoriumSeatNotFoundException(
            Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AUDITORIUM_AND_ROW_NAME, eventVenueId,
            auditoriumName, seatRowName);
    }

    /**
     * For seat number.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @param seatNumber the seat number
     * @return the exception
     */
    public static AuditoriumSeatNotFoundException forSeatNumber(final Long eventVenueId, final String auditoriumName,
            final String seatRowName, final Integer seatNumber) {

        return new AuditoriumSeatNotFoundException(
            Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AUDITORIUM_ROW_NAME_AND_SEAT_NUMBER, eventVenueId,
            auditoriumName, seatRowName, seatNumber);
    }

    /**
     * For event venue and auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the exception
     */
    public static AuditoriumSeatNotFoundException forEventVenueAndAuditorium(final Long eventVenueId, final String auditoriumName) {

        return new AuditoriumSeatNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_EVENT_VENUE_ID_AND_AUDITORIUM, eventVenueId, auditoriumName);
    }

    /**
     * For auditorium id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium seat not found exception
     */
    public static AuditoriumSeatNotFoundException forAuditoriumId(final Long auditoriumId) {

        return new AuditoriumSeatNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_SEATS_FOR_AUDITORIUM_ID, auditoriumId);
    }
}
