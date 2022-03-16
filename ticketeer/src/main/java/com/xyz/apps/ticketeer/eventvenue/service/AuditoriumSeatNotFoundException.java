/*
* Id: AuditoriumSeatNotFoundException.java 16-Mar-2022 12:10:01 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

/**
 * The auditorium seat not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumSeatNotFoundException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 8246173196749690709L;

    /**
     * Instantiates a new auditorium seat not found exception.
     *
     * @param message the message
     */
    public AuditoriumSeatNotFoundException(final String message) {
        super(message);
    }

    /**
     * For event venue and auditorium and row name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @return the auditorium seat not found exception
     */
    public static AuditoriumSeatNotFoundException forEventVenueAndAuditoriumAndRowName(final Long eventVenueId, final String auditoriumName, final String seatRowName) {
        return new AuditoriumSeatNotFoundException("No auditorium seats found for event venue id: " + eventVenueId
            + ", auditorium: " + auditoriumName + " and seat row name: " + seatRowName);
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
    public static AuditoriumSeatNotFoundException forSeatNumber(final Long eventVenueId, final String auditoriumName, final String seatRowName, final Integer seatNumber) {

        return new AuditoriumSeatNotFoundException("No auditorium seats found for event venue id: " + eventVenueId
            + ", auditorium: " + auditoriumName + ", seat row name: " + seatRowName + " and seatNumber: " + seatNumber);
    }

    /**
     * For event venue and auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the exception
     */
    public static AuditoriumSeatNotFoundException forEventVenueAndAuditorium(final Long eventVenueId, final String auditoriumName) {

        return new AuditoriumSeatNotFoundException("No auditorium seats found for event venue id: " + eventVenueId
            + " and auditorium: " + auditoriumName);
    }

    /**
     * For auditorium id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium seat not found exception
     */
    public static AuditoriumSeatNotFoundException forAuditoriumId(final Long auditoriumId) {

        return new AuditoriumSeatNotFoundException("No auditorium seats found for auditorium id: " + auditoriumId);
    }
}
