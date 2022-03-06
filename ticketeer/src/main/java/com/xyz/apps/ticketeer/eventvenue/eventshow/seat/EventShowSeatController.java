/*
* Id: EventShowSeatController.java 06-Mar-2022 4:52:07 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.model.DtoList;

import lombok.extern.log4j.Log4j2;

/**
 * The event show seat controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventshow/seats")

/** The log. */
@Log4j2
@Validated
public class EventShowSeatController {

    /** The event show seat service. */
    @Autowired
    private EventShowSeatService eventShowSeatService;

    /**
     * Gets the event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seats by event show id
     */
    @GetMapping("/{eventShowId}")
    public ResponseEntity<?> getEventShowSeatsByEventShowId(@PathVariable("eventShowId") @NotNull(message = "The event show id cannot be null.") final Long eventShowId) {

        try {
            log.info("Event show: " + eventShowId);
            final EventShowSeatDtoList eventShowSeatDtoList = eventShowSeatService.findEventShowSeatsByEventShowId(eventShowId);
            if (DtoList.isNotEmpty(eventShowSeatDtoList)) {
                log.info("Event show seats: " + eventShowSeatDtoList);
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(eventShowSeatDtoList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No seats found for show: " + eventShowId);
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find seats for event show: "
                    + eventShowId + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Calculate seats total amount.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PostMapping("/amount/calculate")
    public ResponseEntity<?> calculateSeatsTotalAmount(@RequestBody @NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            final Double amount = eventShowSeatService.calculateSeatsTotalAmount(seatIds);
            log.info("Seats total amount: " + amount);
            return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(amount);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find amount for event show seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Are seats available.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PostMapping("/are-available")
    public ResponseEntity<?> areSeatsAvailable(@RequestBody @NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.areSeatsAvailable(seatIds));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to check if seats are available: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Are seats reserved.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PostMapping("/are-reserved")
    public ResponseEntity<?> areSeatsReserved(@RequestBody @NotNull(message = "The seats booking info cannot be null.") final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.areSeatsReserved(eventShowSeatsBookingDto.getSeatIds(), eventShowSeatsBookingDto.getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to check if seats are reserved: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Reserve seats.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PutMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestBody @NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.reserveSeats(seatIds));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to reserve seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Unreserve seats.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PutMapping("/unreserve")
    public ResponseEntity<?> unreserveSeats(@RequestBody @NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.unreserveSeats(seatIds));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to unreserve seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Book seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PutMapping("/book")
    public ResponseEntity<?> bookSeats(@RequestBody @NotNull(message = "The seats booking info cannot be null.") final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.bookSeats(eventShowSeatsBookingDto.getSeatIds(), eventShowSeatsBookingDto.getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to book seats: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Fill booking for reserved seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PutMapping("/reserve-with-booking")
    public ResponseEntity<?> fillBookingForReservedSeats(@RequestBody @NotNull(message = "The seats booking info cannot be null.") final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.fillBookingForReservedSeats(eventShowSeatsBookingDto.getSeatIds(), eventShowSeatsBookingDto.getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to fill booking for seats: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelByBookingId(@RequestBody @NotNull(message = "The booking id cannot be null.") final Long bookingId) {

        try {
            log.info("Booking id to cancel: " + bookingId);
            return ResponseEntity
                .ok().build();
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to cancel booking: "
                    + bookingId + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
