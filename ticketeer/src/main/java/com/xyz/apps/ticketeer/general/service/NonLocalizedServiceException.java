/*
 * Id: NonLocalizedServiceException.java 17-Mar-2022 11:39:28 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.general.service;

import org.springframework.http.HttpStatus;


/**
 * The non localized service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class NonLocalizedServiceException extends LocalizedException {

    /** The serial version UID. */
    private static final long serialVersionUID = -4317479573229067263L;

    /** The http status. */
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    /**
     * Instantiates a new non localized service exception.
     *
     * @param message the message
     */
    public NonLocalizedServiceException(final String message) {

        super(message);
    }

    /**
     * Of.
     *
     * @param message the message
     * @return the non localized service exception
     */
    public static NonLocalizedServiceException of(final String message) {
        return new NonLocalizedServiceException(message);
    }

    /**
     * Of.
     *
     * @param message the message
     * @param httpStatus the http status
     * @return the non localized service exception
     */
    public static NonLocalizedServiceException of(final String message, final HttpStatus httpStatus) {
        return new NonLocalizedServiceException(message).withHttpStatus(httpStatus);
    }

    /**
     * With http status.
     *
     * @param httpStatus the http status
     * @return the non localized service exception
     */
    public NonLocalizedServiceException withHttpStatus(final HttpStatus httpStatus) {

        this.httpStatus = httpStatus;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus httpStatus() {

        return httpStatus;
    }
}
