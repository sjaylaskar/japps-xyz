/*
 * Id: DiscountController.java 05-Mar-2022 2:32:29 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountService;
import com.xyz.apps.ticketeer.util.RestResponse;

import lombok.extern.log4j.Log4j2;


/**
 * The discount controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("pricing/discount")
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
        return RestResponse.created(discountDtoAdded);
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
        return RestResponse.created(discountDtoListAdded);
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
        return RestResponse.accepted(discountDtoUpdated);
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
        return RestResponse.accepted(discountDtoListUpdated);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final String id) {

        log.info("Discount id: " + id);
        discountService.deleteById(id);
        log.info("Discount deleted: " + id);
        return RestResponse.accepted("Deleted discount with id: " + id);
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

        log.info("Discount code: " + offerCode);
        discountService.deleteByOfferCode(offerCode);
        log.info("Discount deleted: " + offerCode);
        return RestResponse.accepted("Deleted discount: " + offerCode);
    }

    /**
     * Gets the discount by id.
     *
     * @param id the id
     * @return the discount by id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final String id) {

        final DiscountDto discountDtoFound = discountService.findById(id);
        return RestResponse.ok(discountDtoFound);
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

        final DiscountDto discountDtoFound = discountService.findByOfferCode(offerCode);
        return RestResponse.ok(discountDtoFound);
    }

    /**
     * Gets the by city id.
     *
     * @param cityId the city id
     * @return the by city id
     */
    @GetMapping(value = "city/{cityId}")
    public ResponseEntity<?> getByCityId(@PathVariable("cityId") @NotNull(
        message = "The city id cannot be blank."
    ) final Long cityId) {

        final DiscountDtoList discountDtoListFound = discountService.findByCityId(cityId);
        return RestResponse.ok(discountDtoListFound);
    }

    /**
     * Gets the by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the by event venue id
     */
    @GetMapping(value = "eventvenue/{eventVenueId}")
    public ResponseEntity<?> getByEventVenueId(@PathVariable("eventVenueId") @NotNull(
        message = "The event venue id cannot be blank."
    ) final Long eventVenueId) {

        final DiscountDtoList discountDtoListFound = discountService.findByEventVenueId(eventVenueId);
        return RestResponse.ok(discountDtoListFound);
    }

    /**
     * All.
     *
     * @return the response entity
     */
    @GetMapping(value = "all")
    public ResponseEntity<?> all() {

        final DiscountDtoList discountDtoListFound = discountService.findAll();
        return RestResponse.ok(discountDtoListFound);
    }

    /**
     * Strategies.
     *
     * @return the response entity
     */
    @GetMapping(value = "/strategies")
    public ResponseEntity<?> strategies() {

        return RestResponse.ok(discountService.findDiscountStrategies());
    }

    /**
     * Types.
     *
     * @return the response entity
     */
    @GetMapping(value = "/types")
    public ResponseEntity<?> types() {

        return RestResponse.ok(discountService.findDiscountTypes());
    }

    /**
     * Show time types.
     *
     * @return the response entity
     */
    @GetMapping(value = "/show-time-types")
    public ResponseEntity<?> showTimeTypes() {

        return RestResponse.ok(discountService.findShowTimeTypes());
    }
}
