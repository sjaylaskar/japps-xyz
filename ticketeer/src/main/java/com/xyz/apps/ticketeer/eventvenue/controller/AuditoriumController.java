/*
 * Id: AuditoriumController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
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

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumCreationDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.service.AuditoriumService;

import lombok.extern.log4j.Log4j2;


/**
 * The auditorium controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventvenue/auditorium")
@Log4j2
@Validated
public class AuditoriumController {

    /** The auditorium service. */
    @Autowired
    private AuditoriumService auditoriumService;

    /**
     * Adds the.
     *
     * @param auditoriumCreationDto the auditorium creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final AuditoriumCreationDto auditoriumCreationDto) {

        log.info("Auditorium: " + auditoriumCreationDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(auditoriumService.add(auditoriumCreationDto));
    }

    /**
     * Adds the all.
     *
     * @param auditoriumCreationDtoList the auditorium creation dto list
     * @return the response entity
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final AuditoriumCreationDtoList auditoriumCreationDtoList) {

        log.info("Auditoriums: " + auditoriumCreationDtoList);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(auditoriumService.addAll(auditoriumCreationDtoList));
    }

    /**
     * Updates the.
     *
     * @param auditoriumDto the auditorium dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final AuditoriumDto auditoriumDto) {

        log.info("Event venue: " + auditoriumDto);
        return ResponseEntity
            .accepted()
            .body(auditoriumService.update(auditoriumDto));
    }

    /**
     * Delete by id.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the response entity
     */
    @DeleteMapping("/delete/eventvenue/{eventVenueId}/auditorium/{auditoriumName}")
    public ResponseEntity<?> deleteByEventVenueIdAndAuditoriumName(@PathVariable("eventVenueId") final Long eventVenueId, @PathVariable("auditoriumName") final String auditoriumName) {

        log.info("Event venue id: " + eventVenueId);
        log.info("Auditorium name: " + auditoriumName);
        auditoriumService.deleteByEventVenueIdAndAuditoriumName(eventVenueId, auditoriumName);
        final String deleteMessage = "Deleted auditorium: " + auditoriumName + " for event venue: " + eventVenueId;
        log.info(deleteMessage);
        return ResponseEntity.accepted().body(deleteMessage);
    }

    /**
     * Gets the auditoriums by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the auditoriums by event venue id.
     */
    @GetMapping("/eventvenue/{eventVenueId}")
    public ResponseEntity<?> getByEventVenueId(@PathVariable("eventVenueId") final Long eventVenueId) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(auditoriumService.findByEventVenueId(eventVenueId));
    }

}
