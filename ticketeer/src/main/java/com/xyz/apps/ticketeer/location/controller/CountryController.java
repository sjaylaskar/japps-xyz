/*
 * Id: CountryController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.apps.ticketeer.location.api.internal.contract.CountryCreationDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryCreationDtoList;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDto;
import com.xyz.apps.ticketeer.location.service.CountryService;
import com.xyz.apps.ticketeer.util.RestResponse;

import lombok.extern.log4j.Log4j2;


/**
 * The country controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("country")
@Log4j2
public class CountryController {

    /** The country service. */
    @Autowired
    private CountryService countryService;

    /**
     * Adds the country.
     *
     * @param countryCreationDto the country creation dto
     * @return the country dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final CountryCreationDto countryCreationDto) {

        log.info("Country: " + countryCreationDto);
        return RestResponse.created(countryService.add(countryCreationDto));
    }

    /**
     * Adds multiple.
     *
     * @param countryDtos the country dtos
     * @return the list of countries
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final CountryCreationDtoList countryCreationDtoList) {

        log.info("Country list: " + countryCreationDtoList);
        return RestResponse.created(countryService.addAll(countryCreationDtoList));
    }

    /**
     * Updates the country.
     *
     * @param countryDto the country dto
     * @return the country dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final CountryDto countryDto) {

        log.info("Country: " + countryDto);
        return RestResponse.accepted(countryService.update(countryDto));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        log.info("Country id: " + id);
        countryService.deleteById(id);
        log.info("Country deleted: " + id);
        return RestResponse.accepted("Deleted country: " + id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable final String code) {

        log.info("Country code: " + code);
        countryService.deleteByCode(code);
        return RestResponse.accepted("Deleted country: " + code);
    }

    /**
     * Gets the country by id.
     *
     * @param id the id
     * @return the country by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        return RestResponse.ok(countryService.findById(id));
    }

    /**
     * Gets the country by code.
     *
     * @param code the code
     * @return the country by code
     */
    @GetMapping(value = "code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable("code") final String code) {

        return RestResponse.ok(countryService.findByCode(code));
    }

    /**
     * All.
     *
     * @return the list
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {

        return RestResponse.ok(countryService.findAll());
    }
}
