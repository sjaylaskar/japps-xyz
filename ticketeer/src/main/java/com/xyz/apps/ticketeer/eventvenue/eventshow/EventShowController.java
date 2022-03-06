/*
 * Id: EventController.java 15-Feb-2022 11:19:17 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.model.general.DtoList;

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
     * @param eventShowDetailsDto the event show details dto
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final EventShowDetailsDto eventShowDetailsDto) {

        try {
            log.info("Event show: " + eventShowDetailsDto);
            final EventShowDto eventShowAdded = eventShowService.add(eventShowDetailsDto);
            log.info("Event show added: " + eventShowAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventShowAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event show: "
                    + eventShowDetailsDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull(message = "The event show id cannot be null") final Long id) {

        try {
            log.info("Event show: " + id);
            eventShowService.delete(id);
            log.info("Event show deleted: " + id);
            return ResponseEntity.accepted()
                .body("Event show deleted for id: " + id);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete event show: " + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Search events by city.
     *
     * @param cityId the city id
     * @return the response entity
     */
    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") final Long cityId) {

        try {
            log.info("Event city: " + cityId);
            final EventShowDtoList eventShowDtoList = eventShowService.findByCityId(cityId);
            if (DtoList.isNotEmpty(eventShowDtoList)) {
                log.info("Events found: " + eventShowDtoList + " in city: " + cityId);
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(eventShowDtoList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No event shows found for city: " + cityId);
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find events for city: " + cityId + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the response entity
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody final EventShowSearchCriteria eventShowSearchCriteria) {

        try {
            log.info("Event show search criteria: " + eventShowSearchCriteria);
            final EventShowDtoList eventShowDtoList = eventShowService.search(eventShowSearchCriteria);
            if (DtoList.isNotEmpty(eventShowDtoList)) {
                log.info("Event shows found: " + eventShowDtoList);
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(eventShowDtoList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No event shows found by given search criteria");
            }
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find event show by search criteria: "
                    + eventShowSearchCriteria + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "The event show id cannot be null") final Long id) {

        try {
            log.info("Event show: " + id);
            return ResponseEntity.status(HttpStatus.FOUND)
                .body(eventShowService.findById(id));
        } catch (final EventShowNotFoundException eventShowNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCauseMessage(eventShowNotFoundException));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to find event show for id: " + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
