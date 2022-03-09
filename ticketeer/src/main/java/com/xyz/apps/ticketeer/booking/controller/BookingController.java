/*
* Id: BookingController.java 03-Mar-2022 1:01:04 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDetailsDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.CancelBookingDto;
import com.xyz.apps.ticketeer.booking.service.BookingNotFoundException;
import com.xyz.apps.ticketeer.booking.service.BookingService;

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
     * @param bookingDto the booking dto
     * @return the response entity
     */
    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody @NotNull(message = "The booking cannot be null.") final BookingDto bookingDto) {

        try {
            log.info("Booking: " + bookingDto);
            final BookingDto bookingReserved = bookingService.reserve(bookingDto);
            if (BooleanUtils.isTrue(bookingReserved.getIsReserved())) {
                log.info("Booking reserved: " + bookingReserved);
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookingReserved);
            } else {
                log.error("Booking could not be reserved: " + bookingReserved);
                return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("The selected seats are no longer available! Please select new seats.");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to reserve booking: " + bookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Confirm.
     *
     * @param bookingDto the booking dto
     * @return the response entity
     */
    @PutMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody @NotNull(message = "The booking cannot be null.") final BookingDto bookingDto) {

        try {
            log.info("Booking: " + bookingDto);
            final BookingDetailsDto bookingDetailsDto = bookingService.confirm(bookingDto);
            if (BooleanUtils.isTrue(bookingDetailsDto.getIsConfirmed())) {
                log.info("Booking confirmed: " + bookingDetailsDto);
                return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(bookingDetailsDto);
            } else {
                log.error("Booking could not be confirmed: " + bookingDetailsDto);
                return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("Payment unsuccessful. Please try again!");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to confirm booking: " + bookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Cancel.
     *
     * @param cancelBookingDto the booking dto
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody @NotNull(message = "The booking cannot be null.") final CancelBookingDto cancelBookingDto) {

        try {
            log.info("Booking: " + cancelBookingDto.getBookingId());
            final boolean isBookingCancelled = bookingService.cancel(cancelBookingDto);
            if (isBookingCancelled) {
                log.info("Booking cancelled: " + cancelBookingDto.getBookingId());
                return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Booking cancelled: " + cancelBookingDto);
            } else {
                log.error("Booking could not be cancelled: " + cancelBookingDto.getBookingId());
                return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("Booking cancellation unsuccesful. Please try again!");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to cancel booking: " + cancelBookingDto.getBookingId() + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
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
            @RequestParam("username") @NotBlank(message = "The username cannot be empty.") final String username,
            @RequestParam("id") @NotNull(message = "The booking id cannot be null.") final Long bookingId) {

        try {
            log.info("Username: " + username);
            log.info("Booking id: " + bookingId);
            return ResponseEntity.status(HttpStatus.OK)
                .body(bookingService.findByUsernameAndId(username, bookingId));
        } catch (final BookingNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find booking for user: " + username + " and booking id: " + bookingId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the details by username.
     *
     * @param username the username
     * @return the by username
     */
    @GetMapping("/details/user/{username}")
    public ResponseEntity<?> getByUsername(
            @PathVariable("username") @NotBlank(message = "The username cannot be empty.") final String username) {

        try {
            log.info("Username: " + username);
            return ResponseEntity.status(HttpStatus.OK)
                .body(bookingService.findByUsername(username));
        } catch (final BookingNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find booking for user: " + username + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}
