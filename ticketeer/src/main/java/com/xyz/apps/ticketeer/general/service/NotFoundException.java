/*
* Id: NotFoundException.java 12-Mar-2022 3:14:03 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.HttpStatus;

/**
 * The not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class NotFoundException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -230187555370136858L;

    /**
     * Instantiates a new not found exception.
     *
     * @param message the message
     */
    public NotFoundException(final String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus httpStatus() {

        return HttpStatus.NOT_FOUND;
    }

}
