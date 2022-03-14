/*
* Id: UserNotFoundException.java 07-Mar-2022 3:52:33 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import com.xyz.apps.ticketeer.general.service.NotFoundException;
import com.xyz.apps.ticketeer.user.resources.Messages;

/**
 * The user not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class UserNotFoundException extends NotFoundException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5323684035822182407L;

    /**
     * Instantiates a new user not found exception.
     *
     * @param id the id
     */
    public UserNotFoundException(final Long id) {

        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_USER_ID, id);
    }

    /**
     * Instantiates a new user not found exception.
     *
     * @param username the username
     */
    public UserNotFoundException(final String username) {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_USERNAME, username);
    }

    /**
     * Instantiates a new user not found exception.
     */
    public UserNotFoundException() {
        super(Messages.resourceBundle(), Messages.MESSAGE_ERROR_NOT_FOUND_USERS);
    }

}
