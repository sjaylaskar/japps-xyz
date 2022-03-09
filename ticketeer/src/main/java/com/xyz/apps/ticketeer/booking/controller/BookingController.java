/*
* Id: BookingController.java 03-Mar-2022 1:01:04 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.controller;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
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
    public ResponseEntity<?> reserve(@RequestBody final BookingDto bookingDto) {

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
    public ResponseEntity<?> confirm(@RequestBody final BookingDto bookingDto) {

        try {
            log.info("Booking: " + bookingDto);
            final BookingDto bookingDtoUpdated = bookingService.confirm(bookingDto);
            if (BooleanUtils.isTrue(bookingDtoUpdated.getIsConfirmed())) {
                log.info("Booking confirmed: " + bookingDtoUpdated);
                return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(bookingDtoUpdated);
            } else {
                log.error("Booking could not be confirmed: " + bookingDtoUpdated);
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
     * @param bookingDto the booking dto
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody final BookingDto bookingDto) {

        try {
            log.info("Booking: " + bookingDto);
            final boolean isBookingCancelled = bookingService.cancel(bookingDto);
            if (isBookingCancelled) {
                log.info("Booking cancelled: " + bookingDto);
                return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Booking cancelled: " + bookingDto);
            } else {
                log.error("Booking could not be cancelled: " + bookingDto);
                return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("Booking cancellation unsuccesful. Please try again!");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to cancel booking: " + bookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}