/*
* Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventshow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class EventShowController {

    /** The event show service. */
    @Autowired
    private EventShowService eventShowService;

    /** The event show model mapper. */
    @Autowired
    private EventShowModelMapper eventShowModelMapper;

    /**
     * Adds the event show.
     *
     * @param eventShowDetailsDto the event show details dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventShowDetailsDto eventShowDetailsDto) {

        try {
            log.info("Event show: " + eventShowDetailsDto);
            final EventShow eventShowAdded = eventShowService.add(eventShowDetailsDto);
            log.info("Event show added: " + eventShowAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventShowModelMapper.toDto(eventShowAdded));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event show: " + eventShowDetailsDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody final EventShowSearchCriteria eventShowSearchCriteria) {
        try {
            log.info("Event show search criteria: " + eventShowSearchCriteria);
            final List<EventShow> eventShowsFound = eventShowService.search(eventShowSearchCriteria);
            if (CollectionUtils.isNotEmpty(eventShowsFound)) {
                log.info("Event shows found: " + eventShowsFound);
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(EventShowDtoList.of(eventShowModelMapper.toDtos(eventShowsFound)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No event shows found by given search criteria");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find event show by search criteria: " + eventShowSearchCriteria + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
