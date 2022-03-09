/*
* Id: User.java 14-Feb-2022 12:48:53 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

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
@Validated
public class User extends com.xyz.apps.ticketeer.general.model.Entity {
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(initialValue = 1, name = "user_seq", allocationSize = 1)
    private Long id;

    /** The username. */
    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = "Username is mandatory.")
    @Size(min = 8, max = 15, message = "The username must be between 8 and 15 characters.")
    private String username;

    /** The password. */
    @Column(nullable = false, length = 15)
    @NotBlank(message = "Password is mandatory.")
    @Size(min = 8, max = 15, message = "The password must be between 8 and 15 characters.")
    private String password;

    /** The firstname. */
    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory.")
    private String firstname;

    /** The lastname. */
    private String lastname;

    /** The email. */
    @Column(nullable = false, unique = true)
    @Email(message = "Not a valid email id.")
    @NotBlank(message = "Email id is mandatory.")
    private String email;

    /** The phone number. */
    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = "Phone number is mandatory.")
    @Size(min = 10, max = 15, message = "The phone number must be between 10 and 15 characters.")
    private String phoneNumber;
}
