/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.resources;

import java.util.ResourceBundle;

/**
 * The messages.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Messages {

    /**
     * Instantiates a new messages.
     */
    private Messages() {

    }

    /** The error message prefix. */
    private static final String ERROR_MESSAGE_PREFIX = "eventvenue.message.error.";

    /** The message error already exists auditorium. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_AUDITORIUM = ERROR_MESSAGE_PREFIX + "alreadyExists.auditorium.forEventVenue";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("module.eventvenue.Messages");
    }
}
