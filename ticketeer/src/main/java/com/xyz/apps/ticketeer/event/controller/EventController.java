/*
 * Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsCreationDto;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsCreationDtoList;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDto;
import com.xyz.apps.ticketeer.event.service.EventService;

import lombok.extern.log4j.Log4j2;


/**
 * The event controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("event")
@Log4j2
@Validated
public class EventController {

    /** The event service. */
    @Autowired
    private EventService eventService;

    /**
     * Adds the event.
     *
     * @param eventDetailsCreationDto the event details creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventDetailsCreationDto eventDetailsCreationDto) {

        log.info("Event details: " + eventDetailsCreationDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(eventService.add(eventDetailsCreationDto));
    }

    /**
     * Adds multiple events.
     *
     * @param eventDetailsCreationDtoList the event details creation dto list
     * @return the response entity
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final EventDetailsCreationDtoList eventDetailsCreationDtoList) {

        log.info("Events list: " + eventDetailsCreationDtoList);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(eventService.addAll(eventDetailsCreationDtoList));
    }

    /**
     * Updates the event.
     *
     * @param eventDetailsDto the event details dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final EventDetailsDto eventDetailsDto) {

        log.info("Event details: " + eventDetailsDto);
        return ResponseEntity
            .accepted()
            .body(eventService.update(eventDetailsDto));
    }

    /**
     * Delete by event id.
     *
     * @param eventId the event id
     * @return the response entity
     */
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteByEventId(@RequestBody final Long eventId) {

        log.info("Event id to delete: " + eventId);
        eventService.delete(eventId);
        log.info("Event deleted: " + eventId);
        return ResponseEntity
            .accepted()
            .body("Event deleted: " + eventId);
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventService.findAll());
    }

    /**
     * Search.
     *
     * @param text the text
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam final String text,
            @RequestParam(defaultValue = "0", required = false) final Integer pageNumber,
            @RequestParam(defaultValue = "1", required = false) final Integer pageSize) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventService.searchByText(text, pageNumber, pageSize));
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventService.findById(id));
    }

    /**
     * Gets the event details by event id.
     *
     * @param eventId the event id
     * @return the event details by event id
     */
    @GetMapping("/details/{eventId}")
    public ResponseEntity<?> getEventDetailsByEventId(@PathVariable("eventId") final Long eventId) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventService.findEventDetailsByEventId(eventId));
    }

    /**
     * Gets the event details by event id.
     *
     * @param eventId the event id
     * @return the event details by event id
     */
    @GetMapping("/search/city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") final Long cityId) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(eventService.findEventDetailsByCityId(cityId));
    }
}
