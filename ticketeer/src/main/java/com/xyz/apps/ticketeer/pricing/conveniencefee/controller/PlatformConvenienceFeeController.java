/*
 * Id: PlatformConvenienceFeeController.java 05-Mar-2022 4:33:51 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.conveniencefee.controller;

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

import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeCreationDto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeDto;
import com.xyz.apps.ticketeer.pricing.conveniencefee.service.PlatformConvenienceFeeService;
import com.xyz.apps.ticketeer.util.RestResponse;

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
@RequestMapping("pricing/platformconveniencefee")
@Log4j2
public class PlatformConvenienceFeeController {

    /** The platformConvenienceFee service. */
    @Autowired
    private PlatformConvenienceFeeService platformConvenienceFeeService;

    /**
     * Adds the platformConvenienceFee.
     *
     * @param platformConvenienceFeeCreationDto the platform convenience fee creation dto
     * @return the response entity
     * @throws Exception In case of a service exception
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final PlatformConvenienceFeeCreationDto platformConvenienceFeeCreationDto)
            throws Exception {

        log.info("PlatformConvenienceFee: " + platformConvenienceFeeCreationDto);
        final PlatformConvenienceFeeDto platformConvenienceFeeDtoAdded = platformConvenienceFeeService.add(
            platformConvenienceFeeCreationDto);
        log.info("PlatformConvenienceFee added: " + platformConvenienceFeeDtoAdded);
        return RestResponse.created(platformConvenienceFeeDtoAdded);
    }

    /**
     * Updates the platformConvenienceFee.
     *
     * @param platformConvenienceFeeDto the platformConvenienceFee dto
     * @return the platformConvenienceFee dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final PlatformConvenienceFeeDto platformConvenienceFeeDto) {

        log.info("PlatformConvenienceFee: " + platformConvenienceFeeDto);
        final PlatformConvenienceFeeDto platformConvenienceFeeDtoUpdated = platformConvenienceFeeService.update(
            platformConvenienceFeeDto);
        log.info("PlatformConvenienceFee updated: " + platformConvenienceFeeDtoUpdated);
        return RestResponse.accepted(platformConvenienceFeeDtoUpdated);
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final String id) {

        log.info("PlatformConvenienceFee id: " + id);
        platformConvenienceFeeService.deleteById(id);
        log.info("PlatformConvenienceFee deleted: " + id);
        return RestResponse.accepted("Deleted platformConvenienceFee with id: " + id);
    }

    /**
     * Gets the percentage.
     *
     * @return the percentage
     */
    @GetMapping(value = "/percentage")
    public ResponseEntity<?> getPercentage() {

        final Double percentage = platformConvenienceFeeService.findPercentage();
        log.info("Platform covenience fee percentage: " + percentage);
        return RestResponse.ok(percentage);
    }
}
