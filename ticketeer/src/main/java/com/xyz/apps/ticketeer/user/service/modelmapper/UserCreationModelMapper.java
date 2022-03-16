/*
* Id: UserModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service.modelmapper;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserCreationDto;
import com.xyz.apps.ticketeer.user.model.User;

/**
 * The user creation model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class UserCreationModelMapper extends GeneralModelMapper<User, UserCreationDto> {

    /**
     * Instantiates a new user creation model mapper.
     */
    public UserCreationModelMapper() {

        super(User.class, UserCreationDto.class);
    }
}
