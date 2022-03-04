/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.conveniencefee;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;


/**
 * The discount dto mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class PlatformConvenienceFeeModelMapper extends AbstractModelMapper<PlatformConvenienceFee, PlatformConvenienceFeeDto> {

    /**
     * Instantiates a new platform convenience fee model mapper.
     */
    public PlatformConvenienceFeeModelMapper() {

        super(PlatformConvenienceFee.class, PlatformConvenienceFeeDto.class);
    }
}
