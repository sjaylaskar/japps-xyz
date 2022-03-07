/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;

/**
 * The user service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class UserService extends GeneralService {

    /** The user repository. */
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds the user.
     *
     * @param user the user
     * @return the event
     */
    public User add(final User user) {
        return userRepository.save(user);
    }

    /**
     * Updates the user.
     *
     * @param user the user
     * @return the user
     */
    public User update(final User user) {
        if (exists(user)) {
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Delete.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean delete(final User user) {
        if (exists(user)) {
            userRepository.delete(user);
            return true;
        }

        return false;
    }

    /**
     * Exists.
     *
     * @param user the user
     * @return true, if successful
     */
    private boolean exists(final User user) {

        return userRepository.existsById(user.getId());
    }

    /**
     * Authenticate.
     *
     * @param basicUserDto the basic user dto
     * @return true, if successful
     */
    public boolean authenticate(@NotNull(message = "The user cannot be null.") final BasicUserDto basicUserDto) {
        if (StringUtils.isNotBlank(basicUserDto.getUsername())
            && StringUtils.isNotBlank(basicUserDto.getPassword())) {
            final User user = userRepository.findByUsernameAndPassword(basicUserDto.getUsername(), basicUserDto.getPassword());
            if (user != null) {
                return true;
            }
            throw new InvalidUserException();
        }
        throw new UserServiceException("Username and Password cannot be blank.");
    }
}
