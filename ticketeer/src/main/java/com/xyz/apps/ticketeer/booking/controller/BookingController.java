/*
 * Id: BookingController.java 03-Mar-2022 1:01:04 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingCancellationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingConfirmationRequestDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingReservationRequestDto;
import com.xyz.apps.ticketeer.booking.service.BookingService;
import com.xyz.apps.ticketeer.util.ResponseBuilder;

import lombok.extern.log4j.Log4j2;


/**
 * The booking controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("booking")
@Log4j2
@Validated
public class BookingController {

    /** The booking service. */
    @Autowired
    private BookingService bookingService;

    /**
     * Reserve.
     *
     * @param bookingReservationRequestDto the booking dto
     * @return the response entity
     */
    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody final BookingReservationRequestDto bookingReservationRequestDto) {

        log.info("Booking: " + bookingReservationRequestDto);
        return ResponseBuilder.created(bookingService.reserve(bookingReservationRequestDto));
    }

    /**
     * Confirm.
     *
     * @param bookingConfirmationRequestDto the booking dto
     * @return the response entity
     */
    @PutMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody final BookingConfirmationRequestDto bookingConfirmationRequestDto) {

        log.info("Booking: " + bookingConfirmationRequestDto);
        return ResponseBuilder.accepted(bookingService.confirm(bookingConfirmationRequestDto));
    }

    /**
     * Cancel.
     *
     * @param bookingCancellationRequestDto the booking dto
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody final BookingCancellationRequestDto bookingCancellationRequestDto) {

        log.info("Booking: " + bookingCancellationRequestDto.getBookingId());
        bookingService.cancel(bookingCancellationRequestDto);
        return ResponseBuilder.accepted("Booking cancelled: " + bookingCancellationRequestDto.getBookingId());
    }

    /**
     * Gets the details by username and id.
     *
     * @param username the username
     * @param bookingId the booking id
     * @return the by username and id
     */
    @GetMapping("/details")
    public ResponseEntity<?> getByUsernameAndId(
            @RequestParam("username") final String username,
            @RequestParam("id") final Long bookingId) {

        log.info("Username: " + username);
        log.info("Booking id: " + bookingId);
        return ResponseBuilder.ok(bookingService.findByUsernameAndId(username, bookingId));
    }

    /**
     * Gets the details by username.
     *
     * @param username the username
     * @return the by username
     */
    @GetMapping("/details/user/{username}")
    public ResponseEntity<?> getByUsername(
            @PathVariable("username") final String username) {

        log.info("Username: " + username);
        return ResponseBuilder.ok(bookingService.findByUsername(username));
    }
}
