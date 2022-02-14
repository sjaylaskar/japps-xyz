/*
* Id: CountryDTOMapper.java 14-Feb-2022 6:37:19 am SubhajoyLaskar
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
 * The country data transfer object mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CountryModelMapper extends AbstractModelMapper {

    /**
     * To entity.
     *
     * @param countryDto the country dto
     * @return the country
     */
    protected Country toEntity(final CountryDto countryDto) {
        if (countryDto == null) {
            return null;
        }
        return modelMapper.map(countryDto, Country.class);
    }

    /**
     * To entities.
     *
     * @param countryDtos the country dtos
     * @return the list of entities
     */
    protected List<Country> toEntities(final List<CountryDto> countryDtos) {
        if (CollectionUtils.isEmpty(countryDtos)) {
            return null;
        }
        return countryDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * To dto.
     *
     * @param country the country
     * @return the country dto
     */
    protected CountryDto toDto(final Country country) {
        if (country == null) {
            return null;
        }
        return modelMapper.map(country, CountryDto.class);
    }

    /**
     * To dtos.
     *
     * @param countries the countries
     * @return the list of dtos.
     */
    protected List<CountryDto> toDtos(final List<Country> countries) {
        if (CollectionUtils.isEmpty(countries)) {
            return null;
        }

        return countries.stream().map(this::toDto).collect(Collectors.toList());
    }

}
