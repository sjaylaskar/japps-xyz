/*
 * Id: DiscountAddFailedException.java 05-Mar-2022 1:03:16 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.util.CollectionUtil;

/**
 * The discount update failed exception.
 */
@ValidateOnExecution(type = ExecutableType.CONSTRUCTORS)
public class DiscountUpdateFailedException extends DiscountServiceException {

    /** The serial version UID. */
    private static final long serialVersionUID = 5312959845566550447L;

    /**
     * Instantiates a new discount update failed exception.
     *
     * @param discountDtos the discount dtos
     */
    public DiscountUpdateFailedException(@NotEmpty(message = "No discounts specified.") final Collection<DiscountDto> discountDtos) {

        super("Failed to update discount(s): " + CollectionUtil.stringify(discountDtos));
    }
}
