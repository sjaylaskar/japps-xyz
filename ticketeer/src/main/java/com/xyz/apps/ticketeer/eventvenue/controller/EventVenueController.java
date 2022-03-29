/*
 * Id: EventVenueController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.controller;

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

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueUpdationDto;
import com.xyz.apps.ticketeer.eventvenue.service.EventVenueService;
import com.xyz.apps.ticketeer.util.RestResponse;

import lombok.extern.log4j.Log4j2;


/**
 * The event venue controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventvenue")
@Log4j2
@Validated
public class EventVenueController {

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    /**
     * Adds the event venue.
     *
     * @param eventVenueDetailsDto the event venue details dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventVenueCreationDto eventVenueCreationDto) {

        log.info("Event venue: " + eventVenueCreationDto);
        return RestResponse.created(eventVenueService.add(eventVenueCreationDto));
    }

    /**
     * Updates the.
     *
     * @param eventVenueUpdationDto the event venue updation dto
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final EventVenueUpdationDto eventVenueUpdationDto) {

        log.info("Event venue: " + eventVenueUpdationDto);
        return RestResponse.accepted(eventVenueService.update(eventVenueUpdationDto));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {

        log.info("Event venue id: " + id);
        eventVenueService.delete(id);
        log.info("Event venue deleted: " + id);
        return RestResponse.accepted("Deleted event venue with id: " + id);
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        return RestResponse.ok(eventVenueService.findById(id));
    }

    /**
     * Gets the by city id.
     *
     * @param cityId the city id
     * @return the by city id
     */
    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") final Long cityId) {

        return RestResponse.ok(eventVenueService.findByCityId(cityId));
    }
}
