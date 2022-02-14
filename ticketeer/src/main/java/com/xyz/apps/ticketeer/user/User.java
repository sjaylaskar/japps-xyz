/*
* Id: User.java 14-Feb-2022 12:48:53 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import javax.persistence.Entity;

import com.xyz.apps.ticketeer.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The user.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity

/**
 * Gets the email.
 *
 * @return the email
 */
@Getter

/**
 * Sets the email.
 *
 * @param email the new email
 */
@Setter

/**
 * {@inheritDoc}
 */
@ToString
public class User extends AbstractEntity {

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The firstname. */
    private String firstname;

    /** The lastname. */
    private String lastname;

    /** The email. */
    private String email;
}
