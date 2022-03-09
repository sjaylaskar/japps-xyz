/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.conveniencefee.model;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;
import com.xyz.apps.ticketeer.pricing.conveniencefee.api.internal.contract.PlatformConvenienceFeeDto;


/**
 * The discount dto mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class PlatformConvenienceFeeModelMapper extends GeneralModelMapper<PlatformConvenienceFee, PlatformConvenienceFeeDto> {

    /**
     * Instantiates a new platform convenience fee model mapper.
     */
    public PlatformConvenienceFeeModelMapper() {

        super(PlatformConvenienceFee.class, PlatformConvenienceFeeDto.class);
    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {
        final TypeMap<PlatformConvenienceFee, PlatformConvenienceFeeDto> platformConvenienceFeeEntityToDtoMap = modelMapper.createTypeMap(PlatformConvenienceFee.class, PlatformConvenienceFeeDto.class);
        platformConvenienceFeeEntityToDtoMap
        .addMappings(
            mapper -> mapper.using(ModelConverter.OBJECTID_TO_STRING_CONVERTER).map(PlatformConvenienceFee::getId, PlatformConvenienceFeeDto::setId)
          );

        final TypeMap<PlatformConvenienceFeeDto, PlatformConvenienceFee> platformConvenienceFeeDtoToEntityMap = modelMapper.createTypeMap(PlatformConvenienceFeeDto.class, PlatformConvenienceFee.class);
        platformConvenienceFeeDtoToEntityMap
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_OBJECTID_CONVERTER).map(PlatformConvenienceFeeDto::getId, PlatformConvenienceFee::setId)
          );
    }
}
