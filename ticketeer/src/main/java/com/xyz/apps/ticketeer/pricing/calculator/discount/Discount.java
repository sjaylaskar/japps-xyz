/*
 * Id: Discount.java 03-Mar-2022 11:22:41 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.mongodb.lang.NonNull;
import com.xyz.apps.ticketeer.pricing.calculator.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.ShowTimeType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The discount.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Document("discounts")
@Getter
@Setter
@ToString
public class Discount extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @MongoId
    private Long id;

    /** The offer code. */
    @NonNull
    @NotBlank(message = "Offer code is required.")
    @Size(min = 5, max = 20, message = "Invalid offer code length - must be between 5 and 20 characters.")
    private String offerCode;

    /** The discount strategy. */
    @NonNull
    @NotNull(message = "Discount strategy is required.")
    @Enumerated(EnumType.STRING)
    private DiscountStrategy discountStrategy;

    /** The applicable city ids. */
    private Set<Long> applicableCityIds = new HashSet<>();

    /** The applicable event venue ids. */
    private Set<Long> applicableEventVenueIds = new HashSet<>();

    /** The min amount. */
    @NonNull
    @NotNull(message = "The min amount must not be null and be at least 0.")
    private Double minAmount = 0d;

    /** The min seats. */
    @NonNull
    @NotNull(message = "The min seats must not be null and be at least 0.")
    private Integer minSeats = 0;

    /** The nth seat. */
    @NonNull
    @NotNull(message = "The nth seat must not be null and be at least 0.")
    private Integer nthSeat;

    /** The show time type. */
    @Enumerated(EnumType.STRING)
    private ShowTimeType showTimeType;

    /** The discount type. */
    @NonNull
    @NotNull(message = "The discount type is required.")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    /** The value. */
    @NotNull(message = "The value of the discount cannot be null")
    @Min(value = 0, message = "The value of the discount must be at least 0.")
    private Double value = 0d;

    /** The start date. */
    private LocalDateTime startTime;

    /** The end date. */
    private LocalDateTime endTime;
}
