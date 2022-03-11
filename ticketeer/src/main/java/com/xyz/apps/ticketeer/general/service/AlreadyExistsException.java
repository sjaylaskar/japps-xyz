/*
* Id: AlreadyExistsException.java 12-Mar-2022 3:15:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.HttpStatus;

/**
 * The already exists exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class AlreadyExistsException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 9090923399726707600L;

    /**
     * Instantiates a new already exists exception.
     *
     * @param message the message
     */
    public AlreadyExistsException(final String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus httpStatus() {

        return HttpStatus.CONFLICT;
    }

}
