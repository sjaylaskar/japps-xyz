/*
 * Id: DiscountAddFailedException.java 05-Mar-2022 1:03:16 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.discount;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;


/**
 * The discount update failed exception.
 */
public class DiscountUpdateFailedException extends RuntimeException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5312959845566550447L;

    /**
     * Instantiates a new discount update failed exception.
     *
     * @param discountDtos the discount dtos
     */
    public DiscountUpdateFailedException(@NotEmpty(message = "No discounts specified.") final Collection<DiscountDto> discountDtos) {

        super("Failed to update discount(s): " + discountDtos.stream().map(DiscountDto::toString).collect(Collectors.joining(",")));
    }
}
