/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.discount;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The discount dto mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class DiscountModelMapper extends AbstractModelMapper<Discount, DiscountDto> {

    public DiscountModelMapper() {

        super(Discount.class, DiscountDto.class);
    }
}
