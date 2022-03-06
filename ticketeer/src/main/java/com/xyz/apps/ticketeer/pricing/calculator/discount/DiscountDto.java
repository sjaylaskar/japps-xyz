/*
* Id: DiscountDto.java 05-Mar-2022 12:44:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.xyz.apps.ticketeer.model.Dto;
import com.xyz.apps.ticketeer.pricing.calculator.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.ShowTimeType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The discount dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class DiscountDto extends Dto<Discount> {

    private String offerCode;

    private DiscountStrategy discountStrategy;

    private Set<Long> applicableCityIds = new HashSet<>();

    private Set<Long> applicableEventVenueIds = new HashSet<>();

    private Double minAmount = 0d;

    private Integer minSeats = 0;

    private Integer nthSeat;

    private ShowTimeType showTimeType;

    private DiscountType discountType;

    private Double value = 0d;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
