/*
* Id: User.java 14-Feb-2022 12:48:53 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    private Long id;

    /** The username. */
    @Column(nullable = false, unique = true)
    @NotNull(message = "Username is mandatory.")
    private String username;

    /** The password. */
    @Column(nullable = false)
    @NotNull(message = "Password is mandatory.")
    private String password;

    /** The firstname. */
    @Column(nullable = false)
    @NotNull(message = "First name is mandatory.")
    private String firstname;

    /** The lastname. */
    private String lastname;

    /** The email. */
    @Column(nullable = false, unique = true)
    @Email(message = "Not a valid email id.")
    @NotNull(message = "Email id is mandatory.")
    private String email;

    /** The phone number. */
    @Column(nullable = false, unique = true)
    @NotNull(message = "Phone number is mandatory.")
    private String phoneNumber;
}
