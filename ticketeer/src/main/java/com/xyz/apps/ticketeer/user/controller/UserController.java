/*
 * Id: UserController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.user.controller;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.user.api.internal.contract.BasicUserDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDto;
import com.xyz.apps.ticketeer.user.model.User;
import com.xyz.apps.ticketeer.user.model.UserModelMapper;
import com.xyz.apps.ticketeer.user.service.UserNotFoundException;
import com.xyz.apps.ticketeer.user.service.UserService;
import com.xyz.apps.ticketeer.user.service.UserServiceException;

import lombok.extern.log4j.Log4j2;


/**
 * The user controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("user")
@Log4j2
@Validated
public class UserController {

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The user model mapper. */
    @Autowired
    private UserModelMapper userModelMapper;

    /**
     * Adds the user.
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final UserDto userDto) {

        try {
            log.info("User: " + userDto);
            final User user = userModelMapper.toEntity(userDto);
            final User userAdded = userService.add(user);
            log.info("User added: " + userAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userModelMapper.toDto(userAdded));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add user: " + userDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Updates the.
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final UserDto userDto) {

        try {
            log.info("UserDto: " + userDto);
            final User user = userModelMapper.toEntity(userDto);
            final User userUpdated = userService.update(user);
            if (userUpdated != null) {
                log.info("User updated: " + userUpdated);
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userModelMapper.toDto(userUpdated));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User: " + userDto + " not found.");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to update user: " + userDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete.
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody final UserDto userDto) {

        try {
            log.info("UserDto: " + userDto);
            final User user = userModelMapper.toEntity(userDto);
            if (userService.delete(user)) {
                log.info("User deleted: " + userDto);
                return ResponseEntity.accepted().body("Deleted user: " + userDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User: " + userDto + " not found.");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete user: " + userDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        try {
            log.info("User id: " + id);
            userService.deleteById(id);
            log.info("User deleted: " + id);
            return ResponseEntity.accepted().body("Deleted user with id: " + id);
        } catch (final UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete user with id: " + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Authenticate.
     *
     * @param basicUserDto the basic user dto
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @NotNull(
        message = "The user cannot be null."
    ) final BasicUserDto basicUserDto) {

        try {
            log.info("User: " + basicUserDto.getUsername());
            return ResponseEntity.ok().body(userService.authenticate(basicUserDto));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        } catch (final UserServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        try {
            final UserDto userDto = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
        } catch (final UserNotFoundException userNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(userNotFoundException).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find user: "
                + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}
