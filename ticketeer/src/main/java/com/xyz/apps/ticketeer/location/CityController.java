/*
 * Id: CityController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import java.util.List;
import java.util.stream.Collectors;

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

import com.xyz.apps.ticketeer.model.DtoList;

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
public class CityController {

    /** The city service. */
    @Autowired
    private CityService cityService;

    /** The city model mapper. */
    @Autowired
    private CityModelMapper cityModelMapper;

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
            final City city = cityModelMapper.toEntity(cityDto);
            final City cityAdded = cityService.add(city);
            log.info("City added: " + cityAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityModelMapper.toDto(cityAdded));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add city: " + cityDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Adds multiple.
     *
     * @param cityDtos the city dtos
     * @return the list of cities
     */
    @PostMapping("/add/multiple")
    public ResponseEntity<?> addMultiple(@RequestBody final CityDtoList cityDtoList) {

        try {
            log.info("CityDto list: " + cityDtoList);
            final List<City> cities = cityModelMapper.toEntities(cityDtoList.getCityDtos());
            final List<City> citiesAdded = cityService.addAll(cities);
            log.info("Cities added: " + citiesAdded);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CityDtoList.of(cityModelMapper.toDtos(citiesAdded)));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to add cities: " + cityDtoList + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
            final City city = cityModelMapper.toEntity(cityDto);
            final City cityUpdated = cityService.update(city);
            log.info("City updated: " + cityUpdated);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityModelMapper.toDto(cityUpdated));
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to update city: " + cityDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
            final City city = cityModelMapper.toEntity(cityDto);
            cityService.delete(city);
            log.info("City deleted: " + cityDto);
            return ResponseEntity.accepted().body("Deleted city: " + cityDto);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city: " + cityDto + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city with id: " + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
            log.info("CityDto code: " + code);
            cityService.deleteByCode(code);
            log.info("City deleted: " + code);
            return ResponseEntity.accepted().body("Deleted city: " + code);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete city: " + code + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
            final CityDto cityDto = cityModelMapper.toDto(cityService.findById(id));
            return (cityDto != null)
                ? ResponseEntity.status(HttpStatus.FOUND)
                    .body(cityDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("City: " + id + " not found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + id + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the city by code.
     *
     * @param code the code
     * @return the city by code
     */
    @GetMapping(value = "code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable("code") final String code) {
        try {
            final CityDto cityDto = cityModelMapper.toDto(cityService.findByCode(code));
            return (cityDto != null)
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(cityDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("City: " + code + " not found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + code + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the city by name.
     *
     * @param name the name
     * @return the city by name
     */
    @GetMapping(value = "name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") final String name) {

        try {
            final CityDtoList cityDtoList = CityDtoList.of(cityModelMapper.toDtos(cityService.findByName(name)));
            return (DtoList.isNotEmpty(cityDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(cityDtoList)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("City: " + name + " not found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find city: "
                + name + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the by country code.
     *
     * @param countryCode the country code
     * @return the by country code
     */
    @GetMapping(value = "country/{countryCode}")
    public ResponseEntity<?> getByCountryCode(@PathVariable("countryCode") final String countryCode) {

        try {
            final CityDtoList cityDtoList = CityDtoList.of(cityModelMapper.toDtos(cityService.findByCountryCode(countryCode)));
            return (DtoList.isNotEmpty(cityDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(cityDtoList)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cities not found for country: " + countryCode);
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities for country: "
                + countryCode + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    /**
     * Gets the by country id.
     *
     * @param countryId the country id
     * @return the by country id
     */
    @GetMapping(value = "country/{countryId}")
    public ResponseEntity<?> getByCountryId(@PathVariable("countryId") final Long countryId) {

        try {
            final CityDtoList cityDtoList = CityDtoList.of(cityModelMapper.toDtos(cityService.findByCountry(countryId)));
            return (DtoList.isNotEmpty(cityDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(cityDtoList)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cities not found for country: " + countryId);
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities for country: "
                + countryId + ". Error: " + ExceptionUtils.getRootCauseMessage(exception));
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
            final CityDtoList cityDtoList = CityDtoList.of(cityService.findAll().stream().map(cityModelMapper::toDto).collect(Collectors.toList()));
            return (DtoList.isNotEmpty(cityDtoList))
                ? ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(cityDtoList)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No cities found.");
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to find cities. Error: "
                + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
