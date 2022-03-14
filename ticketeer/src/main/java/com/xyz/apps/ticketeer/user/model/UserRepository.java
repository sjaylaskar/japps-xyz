/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The user repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds the by username and password.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    @Query("select u from User u where u.username = :username and u.password = :password")
    public User findByUsernameAndPassword(@Param("username") final String username,
                                          @Param("password") final String password);

    /**
     * Finds the by username.
     *
     * @param username the username
     * @return the user
     */
    public User findByUsername(final String username);

    /**
     * Finds the by phone number.
     *
     * @param phoneNumber the phone number
     * @return the user
     */
    public User findByPhoneNumber(final String phoneNumber);

    /**
     * Finds the by email.
     *
     * @param email the email
     * @return the user
     */
    public User findByEmail(final String email);
}
