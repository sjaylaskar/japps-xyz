/*
 * Id: DiscountController.java 05-Mar-2022 2:32:29 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.controller;

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

import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountNotFoundException;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountService;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountServiceException;

import lombok.extern.log4j.Log4j2;


/**
 * The discount controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("discount")
@Log4j2
@Validated
public class DiscountController {

    /** The discount service. */
    @Autowired
    private DiscountService discountService;

    /**
     * Adds the discount.
     *
     * @param discountCreationDto the discount dto
     * @return the discount dto
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final DiscountCreationDto discountCreationDto) {

        log.info("Discount: " + discountCreationDto);
        final DiscountDto discountDtoAdded = discountService.add(discountCreationDto);
        log.info("Discount added: " + discountDtoAdded);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(discountDtoAdded);
    }

    /**
     * Adds multiple.
     *
     * @param discountDtos the discount dtos
     * @return the list of discounts
     */
    @PostMapping("/add/all")
    public ResponseEntity<?> addAll(@RequestBody final DiscountCreationDtoList discountCreationDtoList) {

        log.info("Discount list: " + discountCreationDtoList);
        final DiscountDtoList discountDtoListAdded = discountService.addAll(discountCreationDtoList);
        log.info("Discounts added: " + discountDtoListAdded);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(discountDtoListAdded);
    }

    /**
     * Updates the discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody final DiscountDto discountDto) {

        log.info("Discount: " + discountDto);
        final DiscountDto discountDtoUpdated = discountService.update(discountDto);
        log.info("Discount updated: " + discountDtoUpdated);
        return ResponseEntity
            .accepted()
            .body(discountDtoUpdated);
    }

    /**
     * Updates the all.
     *
     * @param discountDtoList the discount dto list
     * @return the response entity
     */
    @PutMapping("/update/all")
    public ResponseEntity<?> updateAll(@RequestBody final DiscountDtoList discountDtoList) {

        log.info("Discount list: " + discountDtoList);
        final DiscountDtoList discountDtoListUpdated = discountService.updateAll(discountDtoList);
        log.info("Discounts updated: " + discountDtoListUpdated);
        return ResponseEntity
            .accepted()
            .body(discountDtoListUpdated);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final String id) {

        try {
            log.info("Discount id: " + id);
            discountService.deleteById(id);
            log.info("Discount deleted: " + id);
            return ResponseEntity.accepted().body("Deleted discount with id: " + id);
        } catch (final DiscountServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete discount with id: "
                    + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Delete by code.
     *
     * @param offerCode the code
     */
    @DeleteMapping("/delete/code/{offerCode}")
    public ResponseEntity<?> deleteByCode(@PathVariable @NotBlank(
        message = "The discount code to delete cannot be null."
    ) final String offerCode) {

        try {
            log.info("Discount code: " + offerCode);
            discountService.deleteByOfferCode(offerCode);
            log.info("Discount deleted: " + offerCode);
            return ResponseEntity.accepted().body("Deleted discount: " + offerCode);
        } catch (final DiscountServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to delete discount: "
                    + offerCode + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the discount by id.
     *
     * @param id the id
     * @return the discount by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final String id) {

        try {
            final DiscountDto discountDtoFound = discountService.findById(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountDtoFound);
        } catch (final DiscountNotFoundException discountNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(discountNotFoundException)
                .getLocalizedMessage());
        } catch (final DiscountServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discount: "
                    + id + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the discount by offer code.
     *
     * @param offerCode the code
     * @return the discount by code
     */
    @GetMapping(value = "offercode/{offerCode}")
    public ResponseEntity<?> getByOfferCode(@PathVariable("offerCode") @NotBlank(
        message = "The discount offer code to fetch cannot be blank."
    ) final String offerCode) {

        try {
            final DiscountDto discountDtoFound = discountService.findByOfferCode(offerCode);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountDtoFound);
        } catch (final DiscountNotFoundException discountNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(discountNotFoundException)
                .getLocalizedMessage());
        } catch (final DiscountServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discount: "
                    + offerCode + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the by city id.
     *
     * @param cityId the city id
     * @return the by city id
     */
    @GetMapping(value = "city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") @NotBlank(
        message = "The city id cannot be blank."
    ) final Long cityId) {

        try {
            final DiscountDtoList discountDtoListFound = discountService.findByCityId(cityId);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountDtoListFound);
        } catch (final DiscountNotFoundException discountNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(discountNotFoundException)
                .getLocalizedMessage());
        } catch (final DiscountServiceException exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discounts by city: "
                    + cityId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Gets the by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the by event venue id
     */
    @GetMapping(value = "eventvenue/{eventVenueId}")
    public ResponseEntity<?> getByEventVenueId(@PathVariable("eventVenueId") @NotBlank(
        message = "The event venue id cannot be blank."
    ) final Long eventVenueId) {

        try {
            final DiscountDtoList discountDtoListFound = discountService.findByEventVenueId(eventVenueId);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountDtoListFound);
        } catch (final DiscountServiceException discountServiceException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(discountServiceException)
                .getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discounts by city: "
                    + eventVenueId + ". Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping(value = "all")
    public ResponseEntity<?> all() {

        try {
            final DiscountDtoList discountDtoListFound = discountService.findAll();
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountDtoListFound);
        } catch (final DiscountServiceException discountServiceException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(discountServiceException)
                .getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discounts. Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Strategies.
     *
     * @return the response entity
     */
    @GetMapping(value = "/strategies")
    public ResponseEntity<?> strategies() {

        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountService.findDiscountStrategies());
        } catch (final DiscountServiceException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception)
                .getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discount strategies. Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Types.
     *
     * @return the response entity
     */
    @GetMapping(value = "/types")
    public ResponseEntity<?> types() {

        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountService.findDiscountTypes());
        } catch (final DiscountServiceException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception)
                .getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find discount types. Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }

    /**
     * Show time types.
     *
     * @return the response entity
     */
    @GetMapping(value = "/show-time-types")
    public ResponseEntity<?> showTimeTypes() {

        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(discountService.findShowTimeTypes());
        } catch (final DiscountServiceException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionUtils.getRootCause(exception)
                .getLocalizedMessage());
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                "Failed to find show time types. Error: " + ExceptionUtils.getRootCause(exception).getLocalizedMessage());
        }
    }
}
