/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;


/**
 * The city model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CityModelMapper extends GeneralModelMapper<City, CityDto> {

    /**
     * Instantiates a new city model mapper.
     */
    public CityModelMapper() {

        super(City.class, CityDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<City, CityDto> cityToCityDtoMap = modelMapper.createTypeMap(City.class, CityDto.class);
        cityToCityDtoMap.addMappings(
            mapper -> mapper.map(city -> city.getCountry().getId(), CityDto::setCountryId));

        final TypeMap<CityDto, City> cityDtoToCityMap = modelMapper.createTypeMap(CityDto.class, City.class);
        cityDtoToCityMap.addMappings(
            mapper -> mapper.map(cityDto -> new Country().id(cityDto.getCountryId()), City::setCountry));
    }

}
