/*
 * Id: AuditoriumNotFoundException.java 06-Mar-2022 10:35:04 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.service;

/**
 * The auditorium not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumNotFoundException extends EventVenueServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -6241245535405802412L;

    /**
     * Instantiates a new auditorium not found exception.
     *
     * @param id the id
     */
    public AuditoriumNotFoundException(final Long id) {

        super("Auditorium not found for id: " + id);
    }

    /**
     * Instantiates a new auditorium not found exception.
     *
     * @param message the message
     */
    public AuditoriumNotFoundException(final String message) {

        super(message);
    }

    /**
     * For event venue id and id.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumId the auditorium id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forEventVenueIdAndId(final Long eventVenueId, final Long auditoriumId) {

        return new AuditoriumNotFoundException("Auditorium not found for event venue id: "
            + eventVenueId + " and auditorium id: " + auditoriumId);
    }

    /**
     * For event venue id and name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the exception
     */
    public static AuditoriumNotFoundException forEventVenueIdAndName(final Long eventVenueId, final String auditoriumName) {

        return new AuditoriumNotFoundException("Auditorium not found for event venue id: "
            + eventVenueId + " and auditorium name: " + auditoriumName);
    }

    /**
     * For event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forEventVenueId(final Long eventVenueId) {

        return new AuditoriumNotFoundException("Auditoriums not found for event venue id: " + eventVenueId);
    }

    /**
     * For id.
     *
     * @param id the id
     * @return the auditorium not found exception
     */
    public static AuditoriumNotFoundException forId(final Long id) {

        return new AuditoriumNotFoundException("Auditoriums not found for id: " + id);
    }
}
