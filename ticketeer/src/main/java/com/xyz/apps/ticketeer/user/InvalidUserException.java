/*
* Id: InvalidUserException.java 06-Mar-2022 4:05:00 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;


/**
 * The InvalidUserException.
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
        super("The username or password is invalid.");
    }
}
