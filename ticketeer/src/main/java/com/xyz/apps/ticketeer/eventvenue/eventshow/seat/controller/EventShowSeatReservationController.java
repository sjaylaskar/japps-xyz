/*
 * Id: EventShowSeatReservationController.java 06-Mar-2022 4:52:07 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.controller;

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

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsBookingRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsCancellationRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsReservationRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.EventShowSeatReservationService;

import lombok.extern.log4j.Log4j2;


/**
 * The event show seat reservation controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventshow/seats/reservation")
@Log4j2
@Validated
public class EventShowSeatReservationController {

    /** The event show seat service. */
    @Autowired
    private EventShowSeatReservationService eventShowSeatReservationService;

    /**
     * Gets the seats by event show.
     *
     * @param eventShowId the event show id
     * @return the seats by event show
     */
    @GetMapping("/seats-for-show/{eventShowId}")
    public ResponseEntity<?> getSeatsByEventShow(@PathVariable("eventShowId") final Long eventShowId) {

        log.info("Event show id: " + eventShowId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventShowSeatReservationService.findEventShowSeatsByEventShowId(eventShowId));
    }

    /**
     * Gets the seats by event show and booking reservation id.
     *
     * @param eventShowId the event show id
     * @param bookingReservationId the booking reservation id
     * @return the seats by event show and booking reservation id
     */
    @GetMapping("/seats-by-reservation")
    public ResponseEntity<?> getSeatsByEventShowAndBookingReservationId(
            @RequestParam final Long eventShowId,
            @RequestParam final String bookingReservationId) {

        log.info("Event show id: " + eventShowId);
        log.info("Booking reservation id: " + bookingReservationId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventShowSeatReservationService.findByEventShowIdAndBookingReservationId(eventShowId, bookingReservationId));
    }

    /**
     * Obtain seat prices.
     *
     * @param eventShowSeatPricesRequestDto the event show seat prices request dto
     * @return the response entity
     */
    @PostMapping("/seat-prices")
    public ResponseEntity<?> obtainSeatPrices(
            @RequestBody final EventShowSeatPricesRequestDto eventShowSeatPricesRequestDto) {

        log.info("Event show seat prices request: " + eventShowSeatPricesRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventShowSeatReservationService.findSeatPrices(eventShowSeatPricesRequestDto));
    }

    /**
     * Reserve.
     *
     * @param eventShowSeatsReservationRequestDto the event show seats reservation request dto
     * @return the response entity
     */
    @PutMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody final EventShowSeatsReservationRequestDto eventShowSeatsReservationRequestDto) {

        log.info("Event show seats reservation request: " + eventShowSeatsReservationRequestDto);
        return ResponseEntity
            .accepted()
            .body(eventShowSeatReservationService.reserve(eventShowSeatsReservationRequestDto));
    }

    /**
     * Book.
     *
     * @param eventShowSeatsBookingRequestDto the event show seats booking request dto
     * @return the response entity
     */
    @PutMapping("/book")
    public ResponseEntity<?> book(@RequestBody final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto) {

        log.info("Event show seats booking request: " + eventShowSeatsBookingRequestDto);
        return ResponseEntity
            .accepted()
            .body(eventShowSeatReservationService.book(eventShowSeatsBookingRequestDto));
    }

    /**
     * Cancel.
     *
     * @param eventShowSeatsCancellationRequestDto the event show seats cancellation request dto
     * @return the response entity
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody final EventShowSeatsCancellationRequestDto eventShowSeatsCancellationRequestDto) {

        log.info("Event show seats cancellation request: " + eventShowSeatsCancellationRequestDto);
        return ResponseEntity
            .accepted().body(eventShowSeatReservationService.cancel(eventShowSeatsCancellationRequestDto));
    }
}
