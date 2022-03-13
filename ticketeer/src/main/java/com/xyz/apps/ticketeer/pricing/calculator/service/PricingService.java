/*
 * Id: PricingService.java 03-Mar-2022 4:56:03 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.general.service.ServiceUtil;
import com.xyz.apps.ticketeer.pricing.calculator.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.pricing.calculator.api.internal.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountService;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfo;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfoModelMapper;


/**
 * The pricing service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
public class PricingService extends GeneralService {

    /** The discount service. */
    @Autowired
    private DiscountService discountService;

    /** The booking price info model mapper. */
    @Autowired
    private BookingPriceInfoModelMapper bookingPriceInfoModelMapper;

    /**
     * Calculate final amount.
     *
     * @param bookingPriceInfo the booking price info
     * @return the double
     */
    public Double calculateFinalAmount(@NotNull(message = "The booking price info cannot be null.") final BookingPriceInfoDto bookingPriceInfoDto) {
        final BookingPriceInfo bookingPriceInfo = bookingPriceInfoModelMapper.toEntity(bookingPriceInfoDto);
        if (bookingPriceInfo != null && bookingPriceInfo.getBaseAmount() != null) {
            if (bookingPriceInfo.getFinalAmount() == null || bookingPriceInfo.getFinalAmount().equals(0d)) {
                bookingPriceInfo.setFinalAmount(bookingPriceInfo.getBaseAmount());
            }

            if (StringUtils.isNotBlank(bookingPriceInfo.getOfferCode())) {
                final DiscountDto discountDto = discountService.findByOfferCode(bookingPriceInfo.getOfferCode());
                if (discountDto != null) {
                    final Discount discount = discountService.toDiscount(discountDto);
                    discount.getDiscountStrategy().accept(new BookingDiscountApplier(bookingPriceInfo, discount));
                }
            }

            ResponseEntity<Double> platformConvenienceFeeResponseEntity = null;
            try {
                platformConvenienceFeeResponseEntity = serviceBeansFetcher().restTemplate().getForEntity(
                    serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_PLATFORM_CONVENIENCE_FEE_PERCENTAGE.get()), Double.class);
            } catch (final HttpStatusCodeException exception) {
                throw new PricingServiceException(exception.getResponseBodyAsString());
            }

            Double platformConvenienceFeePercentage = 0.0d;
            if (ServiceUtil.hasBodyResponseEntity(platformConvenienceFeeResponseEntity)) {
                platformConvenienceFeePercentage = platformConvenienceFeeResponseEntity.getBody();
            }

            return rounded(bookingPriceInfo.getFinalAmount() * (1 + platformConvenienceFeePercentage / 100));
        }

        return 0d;
    }

    /**
     * Rounded.
     *
     * @param amount the amount
     * @return the rounded value
     */
    private static double rounded(final double amount) {
        return (new BigDecimal(Double.toString(amount))
                .setScale(2, RoundingMode.HALF_UP))
                .doubleValue();
    }
}
