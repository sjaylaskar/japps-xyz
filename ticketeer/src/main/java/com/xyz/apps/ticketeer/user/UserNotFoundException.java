/*
* Id: UserNotFoundException.java 07-Mar-2022 3:52:33 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;


/**
 * The user not found exception.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class UserNotFoundException extends UserServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5323684035822182407L;

    /**
     * Instantiates a new user not found exception.
     *
     * @param id the id
     */
    public UserNotFoundException(final Long id) {

        super("User not found with id: " + id);
    }

}
