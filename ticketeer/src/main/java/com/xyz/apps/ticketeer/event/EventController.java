/*
* Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.util.List;

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
 * The event controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("event")
@Log4j2
public class EventController {

    /** The event service. */
    @Autowired
    private EventService eventService;

    /** The event model mapper. */
    @Autowired
    private EventModelMapper eventModelMapper;

    /**
     * Adds the.
     *
     * @param eventDto the event dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventDto eventDto) {

        try {
            log.info("Event: " + eventDto);
            final Event event = eventModelMapper.toEntity(eventDto);
            final Event eventAdded = eventService.add(event);
            log.info("Event added: " + eventAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventModelMapper.toDto(eventAdded));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event: " + eventDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Adds the multiple.
     *
     * @param eventDtoList the event dto list
     * @return the response entity
     */
    @PostMapping("/add/multiple")
    public ResponseEntity<?> addMultiple(@RequestBody final EventDtoList eventDtoList) {

        try {
            log.info("Events list: " + eventDtoList);
            final List<Event> events = eventModelMapper.toEntities(eventDtoList.dtos());
            final List<Event> eventsAdded = eventService.addAll(events);
            log.info("Events added: " + eventsAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EventDtoList.of(eventModelMapper.toDtos(eventsAdded)));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add events: " + eventDtoList + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
