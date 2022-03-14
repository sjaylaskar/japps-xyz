/*
* Id: Messages.java 13-Mar-2022 1:55:56 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.resources;

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
    private static final String ERROR_MESSAGE_PREFIX = "user.message.error.";

    /*
     * Model messages:
     */

    /** The message error required username. */
    public static final String MESSAGE_ERROR_REQUIRED_USERNAME = "{" + ERROR_MESSAGE_PREFIX + "required.username}";

    public static final String MESSAGE_ERROR_REQUIRED_PASSWORD = "{" + ERROR_MESSAGE_PREFIX + "required.password}";

    /** The message error required phone number. */
    public static final String MESSAGE_ERROR_REQUIRED_PHONE_NUMBER = "{" + ERROR_MESSAGE_PREFIX + "required.phoneNumber}";

    /** The message error required email. */
    public static final String MESSAGE_ERROR_REQUIRED_EMAIL = "{" + ERROR_MESSAGE_PREFIX + "required.email}";

    /** The message error required first name. */
    public static final String MESSAGE_ERROR_REQUIRED_FIRST_NAME = "{" + ERROR_MESSAGE_PREFIX + "required.firstname}";

    /** The message error invalid username. */
    public static final String MESSAGE_ERROR_INVALID_USERNAME = "{" + ERROR_MESSAGE_PREFIX + "invalid.username}";

    /** The message error invalid password. */
    public static final String MESSAGE_ERROR_INVALID_PASSWORD = "{" + ERROR_MESSAGE_PREFIX + "invalid.password}";

    /** The message error invalid phone number. */
    public static final String MESSAGE_ERROR_INVALID_PHONE_NUMBER = "{" + ERROR_MESSAGE_PREFIX + "invalid.phoneNumber}";

    /** The message error invalid email. */
    public static final String MESSAGE_ERROR_INVALID_EMAIL = "{" + ERROR_MESSAGE_PREFIX + "invalid.email}";

    /*
     * Other messages:
     */

    /** The message error not null user. */
    public static final String MESSAGE_ERROR_NOT_NULL_USER = ERROR_MESSAGE_PREFIX + "notNull.user";

    /** The message error not null user id. */
    public static final String MESSAGE_ERROR_NOT_NULL_USER_ID = ERROR_MESSAGE_PREFIX + "notNull.user.id";

    /** The message error not blank username. */
    public static final String MESSAGE_ERROR_NOT_BLANK_USERNAME = ERROR_MESSAGE_PREFIX + "notBlank.username";

    /** The message error not blank username and password. */
    public static final String MESSAGE_ERROR_NOT_BLANK_USERNAME_AND_PASSWORD = "notBlank.username.and.password";

    /** The message error not found user id. */
    public static final String MESSAGE_ERROR_NOT_FOUND_USER_ID = ERROR_MESSAGE_PREFIX + "notFound.user.id";

    /** The message error invalid username or password. */
    public static final String MESSAGE_ERROR_INVALID_USERNAME_OR_PASSWORD = ERROR_MESSAGE_PREFIX + "invalid.username.or.password";

    /** The message error already exists username. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_USERNAME = ERROR_MESSAGE_PREFIX + "alreadyExists.username";

    /** The message error already exists phone number. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_PHONE_NUMBER = ERROR_MESSAGE_PREFIX + "alreadyExists.phoneNumber";

    /** The message error already exists email. */
    public static final String MESSAGE_ERROR_ALREADY_EXISTS_EMAIL = ERROR_MESSAGE_PREFIX + "alreadyExists.email";

    /** The message error not found username. */
    public static final String MESSAGE_ERROR_NOT_FOUND_USERNAME = ERROR_MESSAGE_PREFIX + "notFound.username";

    /** The message error not found users. */
    public static final String MESSAGE_ERROR_NOT_FOUND_USERS = ERROR_MESSAGE_PREFIX + "notFound.users";

    /** The message error failed add user. */
    public static final String MESSAGE_ERROR_FAILED_ADD_USER = ERROR_MESSAGE_PREFIX + "failed.add.user";

    /** The message error failed update user. */
    public static final String MESSAGE_ERROR_FAILED_UPDATE_USER = ERROR_MESSAGE_PREFIX + "failed.update.user";

    /**
     * Resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages.user.Messages");
    }
}
