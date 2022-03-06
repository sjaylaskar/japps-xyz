/*
* Id: DiscountType.java 04-Mar-2022 3:53:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * The discount type.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public enum DiscountType {

    /** The percentage. */
    PERCENTAGE,

    /** The amount. */
    AMOUNT;

    public static DiscountType of(final String discountType) {


        return Arrays.asList(values())
            .stream()
            .filter(value -> StringUtils.equalsIgnoreCase(value.name(), discountType))
            .findFirst()
            .orElseThrow(() -> new InvalidDiscountTypeException(discountType));

    }
}
