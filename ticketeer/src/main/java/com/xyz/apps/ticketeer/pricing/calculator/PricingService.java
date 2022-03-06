/*
 * Id: PricingService.java 03-Mar-2022 4:56:03 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.pricing.calculator.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.pricing.calculator.discount.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.DiscountService;
import com.xyz.apps.ticketeer.util.Environment;
import com.xyz.apps.ticketeer.util.WebClientBuilder;


/**
 * The pricing service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
public class PricingService {

    /** The discount service. */
    @Autowired
    private DiscountService discountService;

    /**
     * Calculate final amount.
     *
     * @param bookingPriceInfo the booking price info
     * @return the double
     */
    public Double calculateFinalAmount(@NotNull(message = "The booking price info cannot be null.") final BookingPriceInfo bookingPriceInfo) {
        if (bookingPriceInfo != null && bookingPriceInfo.getBaseAmount() != null) {
            if (bookingPriceInfo.getFinalAmount() == null || bookingPriceInfo.getFinalAmount().equals(0d)) {
                bookingPriceInfo.setFinalAmount(bookingPriceInfo.getBaseAmount());
            }

            if (StringUtils.isNotBlank(bookingPriceInfo.getOfferCode())) {
                final DiscountDto discount = discountService.findByOfferCode(bookingPriceInfo.getOfferCode());
                if (discount != null) {
                    discount.getDiscountStrategy().accept(new BookingDiscountApplier(bookingPriceInfo, discount));
                }
            }

            final Double platformConvenienceFeePercentage
            = WebClientBuilder.get().build().get().uri(Environment.property(ApiPropertyKey.GET_PLATFORM_CONVENIENCE_FEE_PERCENTAGE.get())).retrieve().bodyToMono(Double.class).block();

            return bookingPriceInfo.getFinalAmount() * (1 + platformConvenienceFeePercentage / 100);
        }

        return 0d;
    }
}
