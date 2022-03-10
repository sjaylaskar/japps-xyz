/*
 * Id: CityController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.controller;

import javax.validation.constraints.NotBlank;

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

import com.xyz.apps.ticketeer.location.api.internal.contract.CityDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityDtoList;
import com.xyz.apps.ticketeer.location.service.CityNotFoundException;
import com.xyz.apps.ticketeer.location.service.CityService;
import com.xyz.apps.ticketeer.location.service.CityServiceException;

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
     * @param cityDto the city dto
     * @return the city dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final CityDto cityDto) {

        try {
            log.info("CityDto: " + cityDto);
            final CityDto cityAdded = cityService.add(cityDto);
            log.info("City added: " + cityAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityAdded);
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add city: " + cityDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Adds multiple.
     *
     * @param cityDtos the city dtos
     * @return the list of cities
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final CityDtoList cityDtoList) {

        try {
            log.info("CityDto list: " + cityDtoList);
            final CityDtoList citiesAdded = cityService.addAll(cityDtoList);
            log.info("Cities added: " + citiesAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(citiesAdded);
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add cities: " + cityDtoList + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Updates the city.
     *
     * @param cityDto the city dto
     * @return the city dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final CityDto cityDto) {

        try {
            log.info("CityDto: " + cityDto);
            final CityDto cityUpdated = cityService.update(cityDto);
            log.info("City updated: " + cityUpdated);
            return ResponseEntity
                .accepted()
                .body(cityUpdated);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to update city: " + cityDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete.
     *
     * @param cityDto the city dto
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody final CityDto cityDto) {

        try {
            log.info("CityDto: " + cityDto);
            cityService.delete(cityDto);
            log.info("City deleted: " + cityDto);
            return ResponseEntity.accepted().body("Deleted city: " + cityDto);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city: " + cityDto + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {

        try {
            log.info("CityDto id: " + id);
            cityService.deleteById(id);
            log.info("City deleted: " + id);
            return ResponseEntity.accepted().body("Deleted city with id: " + id);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city with id: " + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable @NotBlank(
        message = "The city code to delete cannot be null."
    ) final String code) {

        try {
            log.info("CityDto code: " + code);
            cityService.deleteByCode(code);
            log.info("City deleted: " + code);
            return ResponseEntity.accepted().body("Deleted city: " + code);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city: " + code + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the city by id.
     *
     * @param id the id
     * @return the city by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Long id) {

        try {
            final CityDto cityDto = cityService.findById(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(cityDto);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the city by code.
     *
     * @param code the code
     * @return the city by code
     */
    @GetMapping(value = "code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable("code") @NotBlank(
        message = "The city code to fetch cannot be blank."
    ) final String code) {

        try {
            final CityDto cityDto = cityService.findByCode(code);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityDto);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + code + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the city by name.
     *
     * @param name the name
     * @return the city by name
     */
    @GetMapping(value = "name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") @NotBlank(
        message = "The city name to fetch cannot be blank."
    ) final String name) {

        try {
            final CityDtoList cityDtoList = cityService.findByName(name);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cityDtoList);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + name + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the by country code.
     *
     * @param countryCode the country code
     * @return the by country code
     */
    @GetMapping(value = "country/code/{countryCode}")
    public ResponseEntity<?> getByCountryCode(
            @PathVariable("countryCode") final String countryCode) {

        try {
            final CityDtoList cityDtoList = cityService.findByCountryCode(countryCode);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cityDtoList);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities for country: "
                + countryCode + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
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

        try {
            final CityDtoList cityDtoList = cityService.findByCountry(countryId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cityDtoList);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities for country: "
                + countryId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
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
            final CityDtoList cityDtoList = cityService.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cityDtoList);
        } catch (final CityNotFoundException cityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(cityNotFoundException).getLocalizedMessage());
        } catch (final CityServiceException exception) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities. Error: "
                + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}
