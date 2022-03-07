/*
 * Id: CountryController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @param countryDto the country dto
     * @return the country dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final CountryDto countryDto) {

        try {
            log.info("CountryDto: " + countryDto);
            final CountryDto countryAdded = countryService.add(countryDto);
            log.info("Country added: " + countryAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(countryAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add country: "
                    + countryDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Adds multiple.
     *
     * @param countryDtos the country dtos
     * @return the list of countries
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final CountryDtoList countryDtoList) {

        try {
            log.info("CountryDto list: " + countryDtoList);
            final CountryDtoList countriesAdded = countryService.addAll(countryDtoList);
            log.info("Countries added: " + countriesAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(countriesAdded);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add countries: " + countryDtoList + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Updates the country.
     *
     * @param countryDto the country dto
     * @return the country dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final CountryDto countryDto) {

        try {
            log.info("CountryDto: " + countryDto);
            final CountryDto countryUpdated = countryService.update(countryDto);
            log.info("Country updated: " + countryUpdated);
            return ResponseEntity
                .accepted()
                .body(countryUpdated);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to update country: " + countryDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete.
     *
     * @param countryDto the country dto
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody final CountryDto countryDto) {

        try {
            log.info("CountryDto: " + countryDto);
            countryService.delete(countryDto);
            log.info("Country deleted: " + countryDto);
            return ResponseEntity.accepted().body("Deleted country: " + countryDto);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete country: " + countryDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        try {

            log.info("CountryDto id: " + id);
            countryService.deleteById(id);
            log.info("Country deleted: " + id);
            return ResponseEntity.accepted().body("Deleted country: " + id);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete country: " + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable final String code) {

        try {
            log.info("CountryDto code: " + code);
            countryService.deleteByCode(code);
            log.info("Country deleted: " + code);
            return ResponseEntity.accepted().body("Deleted country: " + code);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete country: " + code + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Delete by name.
     *
     * @param name the name
     */
    @DeleteMapping("/delete/name/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable final String name) {

        try {
            log.info("CountryDto name: " + name);
            countryService.deleteByName(name);
            log.info("Country deleted: " + name);
            return ResponseEntity.accepted().body("Deleted country: " + name);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete country: " + name + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the country by id.
     *
     * @param id the id
     * @return the country by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        try {
            final CountryDto countryDto = countryService.findById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(countryDto);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find country: "
                + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the country by code.
     *
     * @param code the code
     * @return the country by code
     */
    @GetMapping(value = "code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable("code") final String code) {

        try {
            final CountryDto countryDto = countryService.findByCode(code);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryDto);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find country: "
                + code + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the country by name.
     *
     * @param name the name
     * @return the country by name
     */
    @GetMapping(value = "name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") final String name) {

        try {
            final CountryDto countryDto = countryService.findByName(name);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryDto);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find country: "
                + name + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }

    }

    /**
     * All.
     *
     * @return the list
     */
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        try {
            final CountryDtoList countryDtoList = countryService.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryDtoList);
        } catch (final CountryNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ExceptionUtils.getRootCauseMessage(exception));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities. Error: "
                + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
