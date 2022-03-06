/*
* Id: PricingController.java 06-Mar-2022 3:17:28 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

/**
 * The pricing controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("pricing")
@Log4j2
@Validated
public class PricingController {

    /** The pricing service. */
    @Autowired
    private PricingService pricingService;

    /**
     * Calculate.
     *
     * @param bookingPriceInfoDto the booking price info dto
     * @return the response entity
     */
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody @NotNull(message = "The booking price info cannot be null.") final BookingPriceInfoDto bookingPriceInfoDto) {
        try {
            log.info("Booking price info: " + bookingPriceInfoDto);
            final Double amount = pricingService.calculateFinalAmount(bookingPriceInfoDto);
            log.info("Amount: " + amount);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(amount);
        } catch (final Exception exception) {
            log.error(exception);
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body("Failed to calculate amount. Error: " + ExceptionUtils.getRootCauseMessage(exception));
        }
    }
}
