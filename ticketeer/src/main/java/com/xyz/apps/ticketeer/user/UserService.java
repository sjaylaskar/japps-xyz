/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The user service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class UserService {

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
}
