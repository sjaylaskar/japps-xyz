/*
 * Id: DiscountAddFailedException.java 05-Mar-2022 1:03:16 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;

import com.xyz.apps.ticketeer.util.CollectionUtil;


/**
 * The discount add failed exception.
 */
public class DiscountAddFailedException extends DiscountServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5312959845566550447L;

    /**
     * Instantiates a new discount add failed exception.
     *
     * @param discountDtos the discount dtos
     */
    public DiscountAddFailedException(@NotEmpty(message = "No discounts specified.") final Collection<DiscountDto> discountDtos) {

        super("Failed to add discount(s): " + CollectionUtil.stringify(discountDtos));
    }
}
