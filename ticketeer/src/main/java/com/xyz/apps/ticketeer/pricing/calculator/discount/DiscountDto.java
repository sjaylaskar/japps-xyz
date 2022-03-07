/*
* Id: DiscountDto.java 05-Mar-2022 12:44:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.util.HashSet;
import java.util.Set;

import com.xyz.apps.ticketeer.general.model.Dto;

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

    private String discountStrategy;

    private Set<Long> applicableCityIds = new HashSet<>();

    private Set<Long> applicableEventVenueIds = new HashSet<>();

    private Double minAmount = 0d;

    private Integer minSeats = 0;

    private Integer nthSeat;

    private String showTimeType;

    private String discountType;

    private Double value = 0d;

    private String startTime;

    private String endTime;
}
