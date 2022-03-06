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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> add(@RequestBody final EventVenueDetailsDto eventVenueDetailsDto) {

        try {
            log.info("Event venue: " + eventVenueDetailsDto);
            final EventVenueDto eventVenueAdded = eventVenueService.add(eventVenueDetailsDto);
            log.info("Event venue added: " + eventVenueAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventVenueAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add event venue: " + eventVenueDetailsDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

   /**
    * Gets the by id.
    *
    * @param id the id
    * @return the by id
    */
   @GetMapping("/{id}")
   public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

       try {
           final EventVenueDto eventVenueDto = eventVenueService.findById(id);
           return ResponseEntity.status(HttpStatus.FOUND)
                   .body(eventVenueDto);
       } catch(final EventVenueNotFoundException eventVenueNotFoundException) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCauseMessage(eventVenueNotFoundException));
       } catch (final Exception exception) {
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
               + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
       }
   }
}
