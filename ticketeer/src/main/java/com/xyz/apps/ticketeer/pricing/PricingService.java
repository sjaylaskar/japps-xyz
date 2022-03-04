/*
* Id: PricingService.java 03-Mar-2022 4:56:03 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.booking.BookingDto;
import com.xyz.apps.ticketeer.eventshow.EventShowSeatRepository;
import com.xyz.apps.ticketeer.pricing.conveniencefee.PlatformConvenienceFeeService;

/**
 * The pricing service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class PricingService {

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The platform convenience fee service. */
    @Autowired
    private PlatformConvenienceFeeService platformConvenienceFeeService;

    /**
     * Calculate base amount.
     *
     * @param bookingDto the booking dto
     * @return the double
     */
    public Double calculateBaseAmount(@NotNull(message = "The booking cannot be null.") final BookingDto bookingDto) {
        if (CollectionUtils.isNotEmpty(bookingDto.getEventShowSeatIds())) {
            return eventShowSeatRepository.findTotalAmount(bookingDto.getEventShowSeatIds());
        }
        return 0d;
    }

    /**
     * Calculate final amount.
     *
     * @param bookingDto the booking dto
     * @return the double
     */
    public Double calculateFinalAmount(@NotNull(message = "The booking cannot be null.") final BookingDto bookingDto) {
        if (CollectionUtils.isNotEmpty(bookingDto.getEventShowSeatIds())) {
            double amount = calculateBaseAmount(bookingDto);
            if (StringUtils.isNotBlank(bookingDto.getOfferCode())) {
                // @TODO - Discount module -> Strategies, Types, CRUD, etc.
            }

            amount += (amount * platformConvenienceFeeService.findPercentage());
            return amount;
        }
        return 0d;
    }
}
