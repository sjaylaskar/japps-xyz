/*
 * Id: AuditoriumNotFoundException.java 06-Mar-2022 10:35:04 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.service;

import com.xyz.apps.ticketeer.eventvenue.resources.Messages;
import com.xyz.apps.ticketeer.general.service.NotFoundException;

/**
 * The auditorium not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6241245535405802412L;

    /**
     * Instantiates a new auditorium not found exception.
     *
     * @param id the id
     */
    public AuditoriumNotFoundException(final Long id) {

        this(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_ID, id);
    }

    /**
     * Instantiates a new auditorium not found exception.
     *
     * @param message the message
     */
    public AuditoriumNotFoundException(final String messageKey, final Object ...messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * For event venue id and id.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumId the auditorium id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forEventVenueIdAndId(final Long eventVenueId, final Long auditoriumId) {

        return new AuditoriumNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE_AND_ID, eventVenueId, auditoriumId);
    }

    /**
     * For event venue id and name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the exception
     */
    public static AuditoriumNotFoundException forEventVenueIdAndName(final Long eventVenueId, final String auditoriumName) {

        return new AuditoriumNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE_AND_NAME, eventVenueId, auditoriumName);
    }

    /**
     * For event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forEventVenueId(final Long eventVenueId) {

        return new AuditoriumNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_EVENT_VENUE, eventVenueId);
    }

    /**
     * For id.
     *
     * @param id the id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forId(final Long id) {

        return new AuditoriumNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_AUDITORIUM_FOR_ID, id);
    }
}
