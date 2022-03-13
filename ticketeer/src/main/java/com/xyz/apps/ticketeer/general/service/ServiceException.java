/*
* Id: ServiceException.java 12-Mar-2022 2:58:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.HttpStatus;

/**
 * The service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class ServiceException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7367272733965850656L;


    /**
     * Instantiates a new service exception.
     *
     * @param message the message
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Http status.
     *
     * @return the http status
     */
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
