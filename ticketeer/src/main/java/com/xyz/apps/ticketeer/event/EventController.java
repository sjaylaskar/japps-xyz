/*
* Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.model.DtoList;

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

    /**
     * Adds the event.
     *
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody
            @NotNull(message = "Event details cannot be null") final EventDetailsDto eventDetailsDto) {

        try {
            log.info("Event details: " + eventDetailsDto);
            final EventDetailsDto eventDetailsDtoAdded = eventService.add(eventDetailsDto);
            log.info("Event added: " + eventDetailsDtoAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventDetailsDtoAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event: " + eventDetailsDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Adds multiple events.
     *
     * @param eventDetailsDtoList the event details dto list
     * @return the response entity
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody @NotEmpty(message = "Events list cannot be null or empty.") final EventDetailsDtoList eventDetailsDtoList) {

        try {
            log.info("Events list: " + eventDetailsDtoList);
            final EventDetailsDtoList eventDetailsDtoListAdded = eventService.addAll(eventDetailsDtoList);
            log.info("Events added: " + eventDetailsDtoListAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventDetailsDtoListAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add events: " + eventDetailsDtoList + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        try {
            final EventDetailsDtoList eventDetailsDtoList = eventService.findAll();
            return (DtoList.isNotEmpty(eventDetailsDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(eventDetailsDtoList)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No events found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find events. Error: "
                + ExceptionUtils.getRootCauseMessage(exception));
        }
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
            @NotBlank(message = "Search text is required.") @RequestParam final String text,
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "1") final Integer pageSize) {
        try {
            final EventDetailsDtoList eventDetailsDtoList = eventService.searchByText(text, pageNumber, pageSize);
            return (DtoList.isNotEmpty(eventDetailsDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(eventDetailsDtoList)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No events found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find events. Error: "
                + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
