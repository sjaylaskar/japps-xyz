/*
* Id: PlatformConvenienceFeeController.java 05-Mar-2022 4:33:51 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.conveniencefee;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

/**
 * The platform convenience fee controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("platformconveniencefee")
@Log4j2
public class PlatformConvenienceFeeController {

    /** The platformConvenienceFee service. */
    @Autowired
    private PlatformConvenienceFeeService platformConvenienceFeeService;

    /**
     * Adds the platformConvenienceFee.
     *
     * @param platformConvenienceFeeDto the platformConvenienceFee dto
     * @return the platformConvenienceFee dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final PlatformConvenienceFeeDto platformConvenienceFeeDto) {

        try {
            log.info("PlatformConvenienceFee: " + platformConvenienceFeeDto);
            final PlatformConvenienceFeeDto platformConvenienceFeeDtoAdded = platformConvenienceFeeService.add(platformConvenienceFeeDto);
            log.info("PlatformConvenienceFee added: " + platformConvenienceFeeDtoAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(platformConvenienceFeeDtoAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add platformConvenienceFee: " + platformConvenienceFeeDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Updates the platformConvenienceFee.
     *
     * @param platformConvenienceFeeDto the platformConvenienceFee dto
     * @return the platformConvenienceFee dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final PlatformConvenienceFeeDto platformConvenienceFeeDto) {

        try {
            log.info("PlatformConvenienceFee: " + platformConvenienceFeeDto);
            final PlatformConvenienceFeeDto platformConvenienceFeeDtoUpdated = platformConvenienceFeeService.update(platformConvenienceFeeDto);
            log.info("PlatformConvenienceFee updated: " + platformConvenienceFeeDtoUpdated);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(platformConvenienceFeeDtoUpdated);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to update platformConvenienceFee: " + platformConvenienceFeeDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {

        try {
            log.info("PlatformConvenienceFee id: " + id);
            platformConvenienceFeeService.deleteById(id);
            log.info("PlatformConvenienceFee deleted: " + id);
            return ResponseEntity.accepted().body("Deleted platformConvenienceFee with id: " + id);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete platformConvenienceFee with id: " + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the percentage.
     *
     * @return the percentage
     */
    @GetMapping(value = "/percentage")
    public ResponseEntity<?> getPercentage() {

        try {
            final Double percentage = platformConvenienceFeeService.findPercentage();
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(percentage);
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find platform convenience fee percentage. Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
