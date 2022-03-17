/*
* Id: NotFoundException.java 12-Mar-2022 3:14:03 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.service;

import java.util.ResourceBundle;

import org.springframework.context.MessageSource;
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
     * @param resourceBundle the resource bundle
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public NotFoundException(final ResourceBundle resourceBundle, final String messageKey, final Object ...messageArguments) {
        super(resourceBundle, messageKey, messageArguments);
    }

    /**
     * Instantiates a new not found exception.
     *
     * @param messageSource the message source
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public NotFoundException(final MessageSource messageSource, final String messageKey, final Object ...messageArguments) {
        super(messageSource, messageKey, messageArguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus httpStatus() {

        return HTTP_STATUS;
    }

    /** The http status. */
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

}
