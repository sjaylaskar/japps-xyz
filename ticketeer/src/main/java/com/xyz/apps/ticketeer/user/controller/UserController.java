/*
 * Id: UserController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.user.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.xyz.apps.ticketeer.user.api.internal.contract.UserCreationDto;
import com.xyz.apps.ticketeer.user.api.internal.contract.UserDto;
import com.xyz.apps.ticketeer.user.service.UserService;
import com.xyz.apps.ticketeer.util.RestResponse;

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

/** The log. */
@Log4j2
@Validated
public class UserController {

    /** The user service. */
    @Autowired
    private UserService userService;

    /**
     * Adds the user.
     *
     * @param userCreationDto the user creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final UserCreationDto userCreationDto) {

        log.info("User: " + userCreationDto);
        return RestResponse.created(userService.add(userCreationDto));
    }

    /**
     * Updates the user.
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final UserDto userDto) {

        log.info("UserDto: " + userDto);
        return RestResponse.accepted(userService.update(userDto));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        log.info("User id: " + id);
        userService.deleteById(id);
        log.info("User deleted: " + id);
        return RestResponse.accepted("Deleted user with id: " + id);
    }

    /**
     * Delete by username.
     *
     * @param username the username
     * @return the response entity
     */
    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable final String username) {

        log.info("Username: " + username);
        userService.deleteByUsername(username);
        log.info("User deleted: " + username);
        return RestResponse.accepted("Deleted user with username: " + username);
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

        log.info("User: " + basicUserDto.getUsername());
        return RestResponse.ok(userService.authenticate(basicUserDto));
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {

        return RestResponse.ok(userService.findAll());
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        return RestResponse.ok(userService.findById(id));
    }
}
