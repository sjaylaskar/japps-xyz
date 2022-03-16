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
import com.xyz.apps.ticketeer.pricing.calculator.api.external.ExternalApiUrls;
import com.xyz.apps.ticketeer.pricing.calculator.api.internal.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountService;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfo;
import com.xyz.apps.ticketeer.pricing.calculator.resources.Messages;
import com.xyz.apps.ticketeer.pricing.calculator.service.modelmapper.BookingPriceInfoModelMapper;
import com.xyz.apps.ticketeer.util.MessageUtil;
import com.xyz.apps.ticketeer.util.StringUtil;


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
    public Double calculateFinalAmount(@NotNull(
        message = StringUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_BOOKING_PRICE_INFO
    ) final BookingPriceInfoDto bookingPriceInfoDto) {

        final BookingPriceInfo bookingPriceInfo = bookingPriceInfoModelMapper.toEntity(bookingPriceInfoDto);
        if (bookingPriceInfo != null) {
            if (StringUtils.isNotBlank(bookingPriceInfo.getOfferCode())) {
                final DiscountDto discountDto = discountService.findByOfferCode(bookingPriceInfo.getOfferCode());
                if (discountDto != null) {
                    final Discount discount = discountService.toDiscount(discountDto);
                    discount.getDiscountStrategy().accept(new BookingDiscountApplier(bookingPriceInfo, discount));
                }
            } else {
                BookingDiscountApplier.validateSeatPrices(bookingPriceInfo);
                BookingDiscountApplier.calculateBaseAmount(bookingPriceInfo);
            }

            ResponseEntity<Double> platformConvenienceFeeResponseEntity = null;
            try {
                platformConvenienceFeeResponseEntity = restTemplate().getForEntity(MessageUtil.fromMessageSource(messageSource(),
                    ExternalApiUrls.GET_PLATFORM_CONVENIENCE_FEE_PERCENTAGE), Double.class);
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
