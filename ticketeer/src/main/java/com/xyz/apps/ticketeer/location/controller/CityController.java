/*
 * Id: CityController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.controller;

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

import com.xyz.apps.ticketeer.location.api.internal.contract.CityCreationDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityCreationDtoList;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityDto;
import com.xyz.apps.ticketeer.location.service.CityService;

import lombok.extern.log4j.Log4j2;


/**
 * The city controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("city")
@Log4j2
@Validated
public class CityController {

    /** The city service. */
    @Autowired
    private CityService cityService;

    /**
     * Adds the city.
     *
     * @param cityCreationDto the city dto
     * @return the city dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final CityCreationDto cityCreationDto) {

        log.info("CityDto: " + cityCreationDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cityService.add(cityCreationDto));
    }

    /**
     * Adds multiple.
     *
     * @param cityDtos the city dtos
     * @return the list of cities
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final CityCreationDtoList cityCreationDtoList) {

        log.info("City list: " + cityCreationDtoList);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cityService.addAll(cityCreationDtoList));
    }

    /**
     * Updates the city.
     *
     * @param cityDto the city dto
     * @return the city dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final CityDto cityDto) {

        log.info("CityDto: " + cityDto);
        return ResponseEntity
            .accepted()
            .body(cityService.update(cityDto));
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        log.info("City id: " + id);
        cityService.deleteById(id);
        log.info("City deleted: " + id);
        return ResponseEntity.accepted().body("Deleted city with id: " + id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable final String code) {

        log.info("City code: " + code);
        cityService.deleteByCode(code);
        log.info("City deleted: " + code);
        return ResponseEntity.accepted().body("Deleted city: " + code);
    }

    /**
     * Gets the city by id.
     *
     * @param id the id
     * @return the city by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(cityService.findById(id));
    }

    /**
     * Gets the city by code.
     *
     * @param code the code
     * @return the city by code
     */
    @GetMapping(value = "code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable("code") final String code) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cityService.findByCode(code));
    }

    /**
     * Gets the by country id.
     *
     * @param countryId the country id
     * @return the by country id
     */
    @GetMapping(value = "country/{countryId}")
    public ResponseEntity<?> getByCountryId(
            @PathVariable("countryId") final Long countryId) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cityService.findByCountry(countryId));
    }

    /**
     * All.
     *
     * @return the list
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cityService.findAll());
    }
}
