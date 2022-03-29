/*
 * Id: EventShowSeatController.java 06-Mar-2022 4:52:07 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.controller;

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

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesUpdationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsCreationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.EventShowSeatService;
import com.xyz.apps.ticketeer.util.RestResponse;

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
     * Adds the.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        log.info("Event show seats: " + eventShowSeatsCreationDto);
        return RestResponse.created(eventShowSeatService.add(eventShowSeatsCreationDto));
    }

    /**
     * Updates the.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @return the response entity
     */
    @PutMapping("/update/seatrows")
    public ResponseEntity<?> updateForSeatRows(@RequestBody final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        log.info("Event show seats: " + eventShowSeatsCreationDto);
        return RestResponse.accepted(eventShowSeatService.update(eventShowSeatsCreationDto));
    }

    /**
     * Updates the for seat numbers.
     *
     * @param eventShowSeatPricesUpdationDto the event show seat prices updation dto
     * @return the response entity
     */
    @PutMapping("/update/seatnumbers")
    public ResponseEntity<?> updateForSeatNumbers(
            @RequestBody final EventShowSeatPricesUpdationDto eventShowSeatPricesUpdationDto) {

        log.info("Event show seats: " + eventShowSeatPricesUpdationDto);
        return RestResponse.accepted(eventShowSeatService.update(eventShowSeatPricesUpdationDto));
    }

    /**
     * Delete by event show and seat row.
     *
     * @param eventShowId the event show id
     * @param rowName the row name
     * @return the response entity
     */
    @DeleteMapping("/delete/eventshow/{eventShowId}/seatrow/{rowName}")
    public ResponseEntity<?> deleteSeatRow(
            @PathVariable("eventShowId") final Long eventShowId,
            @PathVariable("rowName") final String rowName) {

        log.info("Event show id: " + eventShowId);
        log.info("Seat row name: " + rowName);
        return RestResponse.accepted("Deleted "
            + eventShowSeatService.deleteByEventShowAndSeatRow(eventShowId, rowName) + " seats.");
    }

    /**
     * Delete seat number.
     *
     * @param eventShowId the event show id
     * @param seatNumber the seat number
     * @return the response entity
     */
    @DeleteMapping("/delete/eventshow/{eventShowId}/seatnumber/{seatNumber}")
    public ResponseEntity<?> deleteSeatNumber(
            @PathVariable("eventShowId") final Long eventShowId,
            @PathVariable("seatnumber") final String seatNumber) {

        log.info("Event show id: " + eventShowId);
        log.info("Seat number: " + seatNumber);
        return RestResponse.accepted("Deleted "
            + eventShowSeatService.deleteByEventShowAndSeatNumber(eventShowId, seatNumber) + " seats.");
    }

    /**
     * Gets the by event show id.
     *
     * @param eventVenueId the event venue id
     * @return the by event show id
     */
    @GetMapping("/event-show-id/{eventShowId}")
    public ResponseEntity<?> getByEventShowId(
            @PathVariable("eventShowId") final Long eventVenueId) {

        return RestResponse.ok(eventShowSeatService.findEventShowSeatsByEventShowId(eventVenueId));
    }
}
