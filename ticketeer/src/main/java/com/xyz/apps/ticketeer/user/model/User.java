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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.user.resources.Messages;

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
@Table(
    uniqueConstraints =
       {@UniqueConstraint(name = "UNIQUE_username", columnNames = "username"),
        @UniqueConstraint(name = "UNIQUE_email", columnNames = "email"),
        @UniqueConstraint(name = "UNIQUE_phoneNumber", columnNames = "phoneNumber")}
)
public class User extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(initialValue = 1, name = "user_seq", allocationSize = 1)
    private Long id;

    /** The username. */
    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_USERNAME)
    @Size(min = 8, max = 15, message = Messages.MESSAGE_ERROR_INVALID_USERNAME)
    private String username;

    /** The password. */
    @Column(nullable = false, length = 15)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_PASSWORD)
    @Size(min = 8, max = 15, message = Messages.MESSAGE_ERROR_INVALID_PASSWORD)
    private String password;

    /** The firstname. */
    @Column(nullable = false)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_FIRST_NAME)
    private String firstname;

    /** The lastname. */
    private String lastname;

    /** The email. */
    @Column(nullable = false, unique = true)
    @Email(message = Messages.MESSAGE_ERROR_INVALID_EMAIL)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_EMAIL)
    private String email;

    /** The phone number. */
    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_PHONE_NUMBER)
    @Size(min = 10, max = 15, message = Messages.MESSAGE_ERROR_INVALID_PHONE_NUMBER)
    private String phoneNumber;
}
