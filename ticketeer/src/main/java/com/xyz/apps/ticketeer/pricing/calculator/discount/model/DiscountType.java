/*
* Id: DiscountType.java 04-Mar-2022 3:53:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.model;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.pricing.calculator.discount.service.InvalidDiscountTypeException;

/**
 * The discount type.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum DiscountType {

    /** The percentage. */
    PERCENTAGE("Applicable for a discount percentage of the total amount."),

    /** The amount. */
    AMOUNT("Applicable for a flat amount reduction.");

    /** The description. */
    private String description;

    /**
     * Instantiates a new discount type.
     *
     * @param description the description
     */
    DiscountType(final String description) {
        this.description = description;
    }

    /**
     * Full description.
     *
     * @return the string
     */
    public String fullDescription() {
        return name() + " : " + description;
    }

    public static DiscountType of(final String discountType) {
        return Arrays.asList(values())
            .stream()
            .filter(value -> StringUtils.equalsIgnoreCase(value.name(), discountType))
            .findFirst()
            .orElseThrow(() -> new InvalidDiscountTypeException(discountType));

    }
}
