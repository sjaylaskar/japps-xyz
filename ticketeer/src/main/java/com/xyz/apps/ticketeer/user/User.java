/*
* Id: User.java 14-Feb-2022 12:48:53 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Getter
@Setter
@ToString
public class User extends com.xyz.apps.ticketeer.model.Entity {
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

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

    /** The phone number. */
    private String phoneNumber;
}
