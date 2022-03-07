/*
* Id: UserDtoList.java 07-Mar-2022 3:42:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;


/**
 * The user dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class UserDtoList extends DtoList<UserDto> {

    /**
     * Instantiates a new user dto list.
     */
    UserDtoList() {

    }

    /**
     * Instantiates a new user dto list.
     *
     * @param userDtos the user dtos
     */
    UserDtoList(final List<UserDto> userDtos) {
        super(userDtos);
    }

    /**
     * Of.
     *
     * @param userDtos the user dtos
     * @return the user dto list
     */
    static UserDtoList of(final List<UserDto> userDtos) {
        if (CollectionUtils.isEmpty(userDtos)) {
            return of();
        }
        return new UserDtoList(userDtos);
    }

    /**
     * Of.
     *
     * @return the user dto list
     */
    static UserDtoList of() {
        return new UserDtoList();
    }
}
