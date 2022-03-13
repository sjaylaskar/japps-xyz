/*
 * Id: Discount.java 03-Mar-2022 11:22:41 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import com.mongodb.lang.NonNull;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;

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
@Validated
public class Discount extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @MongoId
    private ObjectId id;

    /** The offer code. */
    @NonNull
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_OFFER_CODE)
    @Size(min = 5, max = 20, message = Messages.MESSAGE_ERROR_LENGTH_OFFER_CODE)
    private String offerCode;

    /** The discount strategy. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_STRATEGY)
    @Enumerated(EnumType.STRING)
    private DiscountStrategy discountStrategy;

    /** The applicable city ids. */
    private Set<Long> applicableCityIds = new HashSet<>();

    /** The applicable event venue ids. */
    private Set<Long> applicableEventVenueIds = new HashSet<>();

    /** The min amount. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_MIN_AMOUNT)
    private Double minAmount = 0d;

    /** The min seats. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_MIN_SEATS)
    @Min(value = 0, message = Messages.MESSAGE_ERROR_MIN_VALUE_MIN_SEATS)
    private Integer minSeats = 0;

    /** The nth seat. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_NTH_SEAT)
    @Min(value = 0, message = Messages.MESSAGE_ERROR_MIN_VALUE_NTH_SEAT)
    private Integer nthSeat;

    /** The show time type. */
    @Enumerated(EnumType.STRING)
    private ShowTimeType showTimeType;

    /** The discount type. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_TYPE)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    /** The value. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_VALUE)
    private Double value = 0d;

    /** The start date. */
    private LocalDateTime startTime;

    /** The end date. */
    private LocalDateTime endTime;
}
