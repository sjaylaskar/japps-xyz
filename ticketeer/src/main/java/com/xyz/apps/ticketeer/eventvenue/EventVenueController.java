/*
* Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class EventVenueController {

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    @Autowired
    private EventVenueModelMapper eventVenueModelMapper;

    /**
     * Adds the.
     *
     * @param eventDto the event dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventVenueDetailsDto eventVenueDetailsDto) {

        try {
            log.info("Event venue: " + eventVenueDetailsDto);
            final EventVenue eventVenueAdded = eventVenueService.add(eventVenueDetailsDto);
            log.info("Event venue added: " + eventVenueAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventVenueModelMapper.toDto(eventVenueAdded));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event venue: " + eventVenueDetailsDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
