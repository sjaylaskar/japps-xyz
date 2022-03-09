/*
* Id: UserModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.model;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDto;

/**
 * The user model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class UserModelMapper extends GeneralModelMapper<User, UserDto> {

    public UserModelMapper() {

        super(User.class, UserDto.class);
    }
}
