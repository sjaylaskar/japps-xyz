/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.user.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.user.api.internal.contract.BasicUserDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserCreationDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDtoList;
import com.xyz.apps.ticketeer.user.model.User;
import com.xyz.apps.ticketeer.user.model.UserCreationModelMapper;
import com.xyz.apps.ticketeer.user.model.UserModelMapper;
import com.xyz.apps.ticketeer.user.model.UserRepository;
import com.xyz.apps.ticketeer.user.resources.Messages;
import com.xyz.apps.ticketeer.util.StringUtil;

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

    /** The user creation model mapper. */
    @Autowired
    private UserCreationModelMapper userCreationModelMapper;

    /**
     * Adds the user.
     *
     * @param userCreationDto the user creation dto
     * @return the event
     */
    @Transactional(rollbackFor = {Throwable.class})
    public UserDto add(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_USER) final UserCreationDto userCreationDto) {
        validateUserCreation(userCreationDto);

        final User user = userRepository.save(userCreationModelMapper.toEntity(userCreationDto));

        if (user == null) {
            throw new UserServiceException(Messages.MESSAGE_ERROR_FAILED_ADD_USER);
        }

        return userModelMapper.toDto(user);
    }

    /**
     * Updates the user.
     *
     * @param user the user
     * @return the user
     */
    @Transactional(rollbackFor = {Throwable.class})
    public UserDto update(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_USER) final UserDto userDto) {
        if (userDto.getId() == null) {
            throw new UserServiceException(Messages.MESSAGE_ERROR_NOT_NULL_USER_ID);
        }
        if (existsById(userDto.getId())) {
            final User user = userRepository.save(userModelMapper.toEntity(userDto));
            if (user == null) {
                throw new UserServiceException(Messages.MESSAGE_ERROR_FAILED_UPDATE_USER);
            }
            return userModelMapper.toDto(user);
        }
        throw new UserNotFoundException(userDto.getId());
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_USER_ID) final Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Delete.
     *
     * @param username the username
     * @return true, if successful
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByUsername(@NotBlank(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_USERNAME) final String username) {
        final User user = findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        userRepository.delete(user);
    }

    /**
     * Authenticate.
     *
     * @param basicUserDto the basic user dto
     * @return true, if successful
     */
    public boolean authenticate(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_USER) final BasicUserDto basicUserDto) {
        if (StringUtils.isNotBlank(basicUserDto.getUsername())
            && StringUtils.isNotBlank(basicUserDto.getPassword())) {
            final User user = userRepository.findByUsernameAndPassword(basicUserDto.getUsername(), basicUserDto.getPassword());
            if (user != null) {
                return true;
            }
            throw new InvalidUserException();
        }
        throw new UserServiceException(Messages.MESSAGE_ERROR_NOT_BLANK_USERNAME_AND_PASSWORD);
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
        throw new UserNotFoundException();
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the user dto
     */
    public UserDto findById(@NotNull(message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_USER) final Long id) {
        return userModelMapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    /**
     * Exists.
     *
     * @param id the id
     * @return true, if successful
     */
    private boolean existsById(final Long id) {

        return userRepository.existsById(id);
    }

    /**
     * Validate user creation.
     *
     * @param userCreationDto the user creation dto
     */
    private void validateUserCreation(final UserCreationDto userCreationDto) {
        if (findByUsername(userCreationDto.getUsername()) != null) {
            throw UserAlreadyExistsException.forUsername(userCreationDto.getUsername());
        }

        if (findByPhoneNumber(userCreationDto.getPhoneNumber()) != null) {
            throw UserAlreadyExistsException.forPhoneNumber(userCreationDto.getPhoneNumber());
        }

        if (findByEmail(userCreationDto.getEmail()) != null) {
            throw UserAlreadyExistsException.forEmail(userCreationDto.getEmail());
        }
    }

    /**
     * Finds the by username.
     *
     * @param username the username
     * @return the user
     */
    private User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds the by phone number.
     *
     * @param phoneNumber the phone number
     * @return the user
     */
    private User findByPhoneNumber(final String phoneNumber) {
        return userRepository.findByUsername(phoneNumber);
    }

    /**
     * Finds the by email.
     *
     * @param email the email
     * @return the user
     */
    private User findByEmail(final String email) {
        return userRepository.findByUsername(email);
    }
}
