/*
* Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.AbstractModelMapper;

/**
 * The city model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CityModelMapper extends AbstractModelMapper {

    /**
     * To entity.
     *
     * @param cityDto the city dto
     * @return the city
     */
    protected City toEntity(final CityDto cityDto) {
        if (cityDto == null) {
            return null;
        }
        return modelMapper.map(cityDto, City.class);
    }

    /**
     * To entities.
     *
     * @param cityDtos the city dtos
     * @return the list of entities.
     */
    protected List<City> toEntities(final List<CityDto> cityDtos) {
        if (CollectionUtils.isEmpty(cityDtos)) {
            return null;
        }
        return cityDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * To dto.
     *
     * @param city the city
     * @return the city dto
     */
    protected CityDto toDto(final City city) {
        if (city == null) {
            return null;
        }
        return modelMapper.map(city, CityDto.class);
    }

    /**
     * To dtos.
     *
     * @param cities the cities
     * @return the list of cities.
     */
    protected List<CityDto> toDtos(final List<City> cities) {
        if (CollectionUtils.isEmpty(cities)) {
            return null;
        }

        return cities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
