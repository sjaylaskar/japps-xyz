/*
* Id: ServiceException.java 12-Mar-2022 2:58:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import java.util.ResourceBundle;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

/**
 * The service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class ServiceException extends LocalizedException {

    /** The serial version UID. */
    private static final long serialVersionUID = 7367272733965850656L;


    /**
     * Instantiates a new service exception.
     *
     * @param resourceBundle the resource bundle
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public ServiceException(final ResourceBundle resourceBundle, final String messageKey, final Object ...messageArguments) {
        super(resourceBundle, messageKey, messageArguments);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param messageSource the message source
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public ServiceException(final MessageSource messageSource, final String messageKey, final Object ...messageArguments) {
        super(messageSource, messageKey, messageArguments);
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
