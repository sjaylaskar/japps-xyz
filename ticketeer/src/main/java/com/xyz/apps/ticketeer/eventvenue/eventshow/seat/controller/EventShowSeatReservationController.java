/*
 * Id: EventShowSeatController.java 06-Mar-2022 4:52:07 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowWithAuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatModificationResponseDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.EventShowSeatService;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.EventShowSeatsNotFoundException;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowServiceException;
import com.xyz.apps.ticketeer.general.model.DtoList;

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
@Log4j2
@Validated
public class EventShowSeatReservationController {

    /** The event show seat service. */
    @Autowired
    private EventShowSeatService eventShowSeatService;

    /**
     * Gets the seat numbers by seat ids delimited by comma.
     *
     * @param eventShowSeatIds the event show seat ids
     * @return the seat numbers by seat ids
     */
    @GetMapping("/seat-numbers/{eventShowSeatIds}")
    public ResponseEntity<?> getSeatNumbersBySeatIds(@PathVariable("eventShowSeatIds") @NotBlank final String eventShowSeatIds) {

        try {
            log.info("Event show seat ids: " + eventShowSeatIds);
            final String[] eventShowSeatIdsArray = eventShowSeatIds.split(",");
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.findSeatNumbersByIds(
                    Arrays.asList(eventShowSeatIdsArray).stream().map(Long::valueOf).collect(
                        Collectors.toSet())));
        } catch (PatternSyntaxException | NumberFormatException exception) {
            log.error(exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Invalid format for event show seat ids. Please send comma-separated integers as path variable.");
        } catch (final EventShowSeatsNotFoundException exception) {
            log.error(exception);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find seat numbers for ids: "
                    + eventShowSeatIds + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the event show seats by event show id and auditorium idget event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @param auditoriumId the auditorium id
     * @return the event show seats by event show id and auditorium idget event show seats by event show id
     */
    @GetMapping("/search")
    public ResponseEntity<?> getEventShowSeatsByEventShowIdAndAuditoriumId(
            @RequestParam("eventShowId") @NotNull(message = "The event show id cannot be null.") final Long eventShowId,
            @RequestParam("auditoriumId") @NotNull(message = "The auditorium id cannot be null.") final Long auditoriumId) {

        final EventShowWithAuditoriumDto eventShowWithAuditoriumDto = EventShowWithAuditoriumDto.of(eventShowId, auditoriumId);
        try {
            log.info("Event show: " + eventShowWithAuditoriumDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService
                    .findEventShowSeatDetailsByEventShowAndAuditoriumId(eventShowWithAuditoriumDto));
        } catch (final EventShowServiceException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find seats for event show: "
                    + eventShowWithAuditoriumDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seats by event show id
     */
    @GetMapping("/event-show-id/{eventShowId}")
    public ResponseEntity<?> getEventShowSeatsByEventShowId(@PathVariable("eventShowId") @NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        try {
            log.info("Event show: " + eventShowId);
            final EventShowSeatModificationResponseDtoList eventShowSeatDtoList = eventShowSeatService.findEventShowSeatsByEventShowId(eventShowId);
            if (DtoList.isNotEmpty(eventShowSeatDtoList)) {
                log.info("Event show seats: " + eventShowSeatDtoList);
                return ResponseEntity
                    .status(HttpStatus.OK)
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
                    + eventShowId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Calculate seats total amount.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PostMapping("/amount/calculate")
    public ResponseEntity<?> calculateSeatsTotalAmount(@RequestBody @NotEmpty(
        message = "The seat ids cannot be empty."
    ) final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            final Double amount = eventShowSeatService.calculateSeatsTotalAmount(seatIds);
            log.info("Seats total amount: " + amount);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(amount);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find amount for event show seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Are seats available.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PostMapping("/are-available")
    public ResponseEntity<?> areSeatsAvailable(@RequestBody @NotEmpty(
        message = "The seat ids cannot be empty."
    ) final Set<Long> seatIds) {

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
                    + seatIds + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Are seats reserved.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PostMapping("/are-reserved")
    public ResponseEntity<?> areSeatsReserved(@RequestBody @NotNull(
        message = "The seats booking info cannot be null."
    ) final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventShowSeatService.areSeatsReserved(eventShowSeatsBookingDto.getSeatIds(), eventShowSeatsBookingDto
                    .getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to check if seats are reserved: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Reserve seats.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PutMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestBody @NotEmpty(
        message = "The seat ids cannot be empty."
    ) final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            return ResponseEntity
                .accepted()
                .body(eventShowSeatService.reserveSeats(seatIds));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to reserve seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Unreserve seats.
     *
     * @param seatIds the seat ids
     * @return the response entity
     */
    @PutMapping("/unreserve")
    public ResponseEntity<?> unreserveSeats(@RequestBody @NotEmpty(
        message = "The seat ids cannot be empty."
    ) final Set<Long> seatIds) {

        try {
            log.info("Seats IDs: " + seatIds);
            return ResponseEntity
                .accepted()
                .body(eventShowSeatService.unreserveSeats(seatIds));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to unreserve seats: "
                    + seatIds + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Book seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PutMapping("/book")
    public ResponseEntity<?> bookSeats(@RequestBody @NotNull(
        message = "The seats booking info cannot be null."
    ) final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .accepted()
                .body(eventShowSeatService.bookSeats(eventShowSeatsBookingDto.getSeatIds(), eventShowSeatsBookingDto
                    .getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to book seats: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Fill booking for reserved seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the response entity
     */
    @PutMapping("/reserve-with-booking")
    public ResponseEntity<?> fillBookingForReservedSeats(@RequestBody @NotNull(
        message = "The seats booking info cannot be null."
    ) final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        try {
            log.info("Event show seats booking info: " + eventShowSeatsBookingDto);
            return ResponseEntity
                .accepted()
                .body(eventShowSeatService.fillBookingForReservedSeats(eventShowSeatsBookingDto.getSeatIds(),
                    eventShowSeatsBookingDto.getBookingId()));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to fill booking for seats: "
                    + eventShowSeatsBookingDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelByBookingId(@RequestBody @NotNull(
        message = "The booking id cannot be null."
    ) final Long bookingId) {

        try {
            log.info("Booking id to cancel: " + bookingId);
            eventShowSeatService.cancelByBookingId(bookingId);
            return ResponseEntity
                .accepted().build();
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to cancel booking: "
                    + bookingId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}
