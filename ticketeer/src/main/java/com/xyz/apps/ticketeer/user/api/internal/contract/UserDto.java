/*
* Id: UserDto.java 02-Mar-2022 5:53:43 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.user.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The user dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends Dto<User> {

    /** The id. */
    private Long id;

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
