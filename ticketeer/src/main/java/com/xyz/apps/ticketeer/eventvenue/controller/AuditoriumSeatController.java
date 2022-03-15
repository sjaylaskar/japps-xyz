/*
 * Id: AuditoriumSeatController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.controller;

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

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatsCreationDto;
import com.xyz.apps.ticketeer.eventvenue.service.AuditoriumSeatService;

import lombok.extern.log4j.Log4j2;


/**
 * The auditorium seat controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventvenue/auditorium/auditoriumseat")
@Log4j2
@Validated
public class AuditoriumSeatController {

    /** The auditorium seat service. */
    @Autowired
    private AuditoriumSeatService auditoriumSeatService;

    /**
     * Adds the seats.
     *
     * @param auditoriumSeatsCreationDto the auditorium creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        log.info("Auditorium seats: " + auditoriumSeatsCreationDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(auditoriumSeatService.add(auditoriumSeatsCreationDto));
    }

    /**
     * Updates/adds seats.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        log.info("Auditorium seats: " + auditoriumSeatsCreationDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(auditoriumSeatService.update(auditoriumSeatsCreationDto));
    }

    /**
     * Delete by id.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the response entity
     */
    @DeleteMapping("/delete/eventvenue/{eventVenueId}/auditorium/{auditoriumName}")
    public ResponseEntity<?> deleteAllSeatsForAuditorium(
            @PathVariable("eventVenueId") final Long eventVenueId,
            @PathVariable("auditoriumName") final String auditoriumName) {

        log.info("Event venue id: " + eventVenueId);
        log.info("Auditorium name: " + auditoriumName);
        return ResponseEntity.accepted().body("Deleted " + auditoriumSeatService.deleteAllSeatsForAuditorium(eventVenueId, auditoriumName) + " seats.");
    }

    /**
     * Delete seat row.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @return the response entity
     */
    @DeleteMapping("/delete/eventvenue/{eventVenueId}/auditorium/{auditoriumName}/row/{seatRowName}")
    public ResponseEntity<?> deleteSeatRow(
            @PathVariable("eventVenueId") final Long eventVenueId,
            @PathVariable("auditoriumName") final String auditoriumName,
            @PathVariable("seatRowName") final String seatRowName) {

        log.info("Event venue id: " + eventVenueId);
        log.info("Auditorium name: " + auditoriumName);
        log.info("Seat row name: " + seatRowName);
        return ResponseEntity.accepted().body("Deleted " + auditoriumSeatService.deleteSeatRow(eventVenueId, auditoriumName, seatRowName) + " seats.");
    }

    /**
     * Delete by event venue id and auditorium name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @param seatNumber the seat number
     * @return the response entity
     */
    @DeleteMapping("/delete/eventvenue/{eventVenueId}/auditorium/{auditoriumName}/row/{seatRowName}/seatnumber/{seatNumber}")
    public ResponseEntity<?> deleteSeat(
            @PathVariable("eventVenueId") final Long eventVenueId,
            @PathVariable("auditoriumName") final String auditoriumName,
            @PathVariable("seatRowName") final String seatRowName,
            @PathVariable("seatNumber") final Integer seatNumber) {

        log.info("Event venue id: " + eventVenueId);
        log.info("Auditorium name: " + auditoriumName);
        log.info("Seat row name: " + seatRowName);
        log.info("Seat number: " + seatNumber);
        return ResponseEntity.accepted().body("Deleted " + auditoriumSeatService.deleteSeat(eventVenueId, auditoriumName, seatRowName, seatNumber) + " seats.");
    }

    /**
     * Gets the by auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the by auditorium
     */
    @GetMapping("/eventvenue/{eventVenueId}/auditorium/{auditoriumName}")
    public ResponseEntity<?> getByAuditorium(
            @PathVariable("eventVenueId") final Long eventVenueId,
            @PathVariable("auditoriumName") final String auditoriumName) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(auditoriumSeatService.findByEventVenueAndAuditorium(eventVenueId, auditoriumName));
    }

}
