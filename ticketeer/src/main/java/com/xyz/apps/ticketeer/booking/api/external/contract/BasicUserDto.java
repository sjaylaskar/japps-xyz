/*
* Id: UserDto.java 02-Mar-2022 5:53:43 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.external.contract;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The basic user dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class BasicUserDto {

    /** The username. */
    private String username;

    /** The password. */
    private String password;
}
