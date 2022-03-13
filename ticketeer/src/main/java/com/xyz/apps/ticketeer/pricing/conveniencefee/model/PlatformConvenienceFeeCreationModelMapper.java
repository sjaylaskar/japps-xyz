/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.conveniencefee.model;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeCreationDto;


/**
 * The platform convenience fee creation model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class PlatformConvenienceFeeCreationModelMapper extends GeneralModelMapper<PlatformConvenienceFee, PlatformConvenienceFeeCreationDto> {

    /**
     * Instantiates a new platform convenience fee creation model mapper.
     */
    public PlatformConvenienceFeeCreationModelMapper() {

        super(PlatformConvenienceFee.class, PlatformConvenienceFeeCreationDto.class);
    }
}
