/*
* Id: DiscountDto.java 05-Mar-2022 12:44:58 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The discount creation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountCreationDto extends Dto<Discount> implements DiscountContract {

    /** The offer code. */
    private String offerCode;

    /** The discount strategy. */
    private String discountStrategy;

    /** The applicable city ids. */
    private Set<Long> applicableCityIds = new HashSet<>();

    /** The applicable event venue ids. */
    private Set<Long> applicableEventVenueIds = new HashSet<>();

    /** The min amount. */
    private Double minAmount = 0d;

    /** The min seats. */
    private Integer minSeats = 0;

    /** The nth seat. */
    private Integer nthSeat;

    /** The show time type. */
    private String showTimeType;

    /** The discount type. */
    private String discountType;

    /** The value. */
    private Double value = 0d;

    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;
}
