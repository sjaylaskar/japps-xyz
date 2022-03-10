/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.user.api.internal.contract.BasicUserDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDtoList;
import com.xyz.apps.ticketeer.user.model.User;
import com.xyz.apps.ticketeer.user.model.UserModelMapper;
import com.xyz.apps.ticketeer.user.model.UserRepository;

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

    /** The user mapper. */
    @Autowired
    private UserModelMapper userModelMapper;

    /**
     * Adds the user.
     *
     * @param user the user
     * @return the event
     */
    @Transactional(rollbackFor = {Throwable.class})
    public User add(@NotNull(message = "The user cannot be null.") final User user) {
        return userRepository.save(user);
    }

    /**
     * Updates the user.
     *
     * @param user the user
     * @return the user
     */
    @Transactional(rollbackFor = {Throwable.class})
    public User update(@NotNull(message = "The user cannot be null.") final User user) {
        if (user.getId() == null) {
            throw new UserServiceException("The user id to update cannot be null.");
        }
        if (exists(user)) {
            return userRepository.save(user);
        }
        throw new UserNotFoundException(user.getId());
    }

    /**
     * Delete.
     *
     * @param user the user
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    public boolean delete(@NotNull(message = "The user cannot be null.") final User user) {
        if (user.getId() == null) {
            throw new UserServiceException("The user id to update cannot be null.");
        }
        if (exists(user)) {
            userRepository.delete(user);
            return true;
        }

        throw new UserNotFoundException(user.getId());
    }

    /**
     * Exists.
     *
     * @param user the user
     * @return true, if successful
     */
    private boolean exists(@NotNull(message = "The user cannot be null.") final User user) {

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

    /**
     * Finds the all.
     *
     * @return the user dto list
     */
    public UserDtoList findAll() {
        final List<User> users = userRepository.findAll();
        if (CollectionUtils.isNotEmpty(users)) {
            return UserDtoList.of(userModelMapper.toDtos(users));
        }
        throw new UserServiceException("No users found.");
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotNull(message = "The user id cannot be null.") final Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the user dto
     */
    public UserDto findById(@NotNull(message = "The user id cannot be null.") final Long id) {
        return userModelMapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }
}
