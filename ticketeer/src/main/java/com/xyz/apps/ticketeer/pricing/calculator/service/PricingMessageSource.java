/*
 * Id: MessageResourceFinder.java 13-Mar-2022 1:42:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.general.service.ServiceMessageSource;


/**
 * The pricing message source.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class PricingMessageSource extends ServiceMessageSource {

    /**
     * Adds the message sources.
     */
    @PostConstruct
    private void add() {
        super.add("classpath:module/pricing/calculator/Messages",
                  "classpath:module/pricing/calculator/ExternalApiUrls",
                  "classpath:module/pricing/calculator/BookingDiscountApplierMessages");
    }
}
