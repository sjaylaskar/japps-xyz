/*
 * Id: CityServiceException.java 07-Mar-2022 1:34:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

/**
 * The city service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class CityServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = -241616310168263221L;

    /**
     * Instantiates a new city service exception.
     *
     * @param message the message
     */
    public CityServiceException(final String message) {

        super(message);
    }
}
