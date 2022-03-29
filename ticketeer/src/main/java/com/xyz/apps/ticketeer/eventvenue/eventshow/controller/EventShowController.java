/*
 * Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowCreationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowSearchCriteria;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowService;
import com.xyz.apps.ticketeer.util.RestResponse;

import lombok.extern.log4j.Log4j2;


/**
 * The event show controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("eventshow")
@Log4j2
@Validated
public class EventShowController {

    /** The event show service. */
    @Autowired
    private EventShowService eventShowService;

    /**
     * Adds the event show.
     *
     * @param eventShowCreationDto the event show creation dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventShowCreationDto eventShowCreationDto) {

        log.info("Event show: " + eventShowCreationDto);
        return RestResponse.created(eventShowService.add(eventShowCreationDto));
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {

        log.info("Event show: " + id);
        eventShowService.delete(id);
        log.info("Event show deleted: " + id);
        return RestResponse.accepted("Event show deleted for id: " + id);
    }

    /**
     * Search events by city.
     *
     * @param cityId the city id
     * @return the response entity
     */
    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") final Long cityId) {

        log.info("Event city: " + cityId);
        return RestResponse.ok(eventShowService.findByCityId(cityId));
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) final Long cityId,
            @RequestParam(required = false) final Long eventId,
            @RequestParam(required = false) final String date) {

        final EventShowSearchCriteria eventShowSearchCriteria = EventShowSearchCriteria.of(cityId, eventId, date);
        log.info("Event show search criteria: " + eventShowSearchCriteria);
        return RestResponse.ok(eventShowService.search(eventShowSearchCriteria));
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "The event show id cannot be null") final Long id) {

        log.info("Event show: " + id);
        return RestResponse.ok(eventShowService.findById(id));
    }

    /**
     * Gets the details by id.
     *
     * @param id the id
     * @return the details by id
     */
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetailsById(@PathVariable("id") @NotNull(
        message = "The event show id cannot be null"
    ) final Long id) {

        log.info("Event show: " + id);
        return RestResponse.ok(eventShowService.findDetailedInfoById(id));
    }
}
