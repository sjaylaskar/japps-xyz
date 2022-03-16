/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service.modelmapper;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityCreationDto;
import com.xyz.apps.ticketeer.location.model.City;
import com.xyz.apps.ticketeer.location.model.Country;


/**
 * The city creation model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CityCreationModelMapper extends GeneralModelMapper<City, CityCreationDto> {

    /**
     * Instantiates a new city model mapper.
     */
    public CityCreationModelMapper() {

        super(City.class, CityCreationDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final Converter<Long, Country> countryIdToCountryConverter = converter -> new Country().id(converter.getSource());
        final TypeMap<CityCreationDto, City> cityDtoToCityMap = modelMapper.createTypeMap(CityCreationDto.class, City.class);
        cityDtoToCityMap.addMappings(
            mapper -> mapper.using(countryIdToCountryConverter).map(CityCreationDto::getCountryId, City::setCountry));
    }

}
