/*
* Id: AuditoriumNotFoundException.java 06-Mar-2022 10:35:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;


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
}
