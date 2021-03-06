/*
* Id: UserServiceException.java 06-Mar-2022 4:12:44 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import com.xyz.apps.ticketeer.general.service.ServiceException;
import com.xyz.apps.ticketeer.user.resources.Messages;

/**
 * The user service exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class UserServiceException extends ServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = -7482018046156320846L;

    /**
     * Instantiates a new user service exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public UserServiceException(final String messageKey, final Object ...messageArguments) {
        super(Messages.resourceBundle(), messageKey, messageArguments);
    }
}
