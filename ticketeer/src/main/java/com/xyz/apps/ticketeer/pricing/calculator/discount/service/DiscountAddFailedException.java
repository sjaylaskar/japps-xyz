/*
 * Id: DiscountAddFailedException.java 05-Mar-2022 1:03:16 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import java.util.Collection;

import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
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
     * @param discountCreationDtos the discount creation dtos
     */
    public DiscountAddFailedException(final Collection<DiscountCreationDto> discountCreationDtos) {

        super(Messages.MESSAGE_ERROR_FAILURE_ADD_DISCOUNT, CollectionUtil.stringify(discountCreationDtos));
    }
}
