/*
* Id: UserAlreadyExistsException.java 14-Mar-2022 4:18:01 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import com.xyz.apps.ticketeer.general.service.AlreadyExistsException;
import com.xyz.apps.ticketeer.user.resources.Messages;


/**
 * The user already exists exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class UserAlreadyExistsException extends AlreadyExistsException {

    /** The serial version UID. */
    private static final long serialVersionUID = 576518295395453763L;

    /**
     * Instantiates a new user already exists exception.
     *
     * @param messageKey the message key
     * @param messageArguments the message arguments
     */
    public UserAlreadyExistsException(final String messageKey, final Object... messageArguments) {

        super(Messages.resourceBundle(), messageKey, messageArguments);
    }

    /**
     * For username.
     *
     * @param username the username
     * @return the user already exists exception
     */
    public static UserAlreadyExistsException forUsername(final String username) {

        return new UserAlreadyExistsException(Messages.MESSAGE_ERROR_ALREADY_EXISTS_USERNAME, username);
    }

    /**
     * For phone number.
     *
     * @param phoneNumber the phone number
     * @return the user already exists exception
     */
    public static UserAlreadyExistsException forPhoneNumber(final String phoneNumber) {

        return new UserAlreadyExistsException(Messages.MESSAGE_ERROR_ALREADY_EXISTS_PHONE_NUMBER, phoneNumber);
    }

    /**
     * For email.
     *
     * @param email the email
     * @return the user already exists exception
     */
    public static UserAlreadyExistsException forEmail(final String email) {

        return new UserAlreadyExistsException(Messages.MESSAGE_ERROR_ALREADY_EXISTS_EMAIL, email);
    }
}
