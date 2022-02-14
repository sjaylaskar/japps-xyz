/*
* Id: CountryDTOMapper.java 14-Feb-2022 6:37:19 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

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

}
