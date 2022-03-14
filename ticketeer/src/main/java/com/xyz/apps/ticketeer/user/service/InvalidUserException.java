/*
* Id: InvalidUserException.java 06-Mar-2022 4:05:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import org.springframework.http.HttpStatus;

import com.xyz.apps.ticketeer.user.resources.Messages;

/**
 * The invalid user exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class InvalidUserException extends UserServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 4570462848994626746L;

    /**
     * Instantiates a new invalid user exception.
     */
    public InvalidUserException() {
        super(Messages.MESSAGE_ERROR_INVALID_USERNAME_OR_PASSWORD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus httpStatus() {

        return HttpStatus.UNAUTHORIZED;
    }
}
