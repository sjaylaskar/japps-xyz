/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.DtoList;


/**
 * The country creation dto list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryCreationDtoList extends DtoList<CountryCreationDto> {

    /**
     * Instantiates a new country creation dto list.
     */
    public CountryCreationDtoList() {

    }

    /**
     * Instantiates a new country creation dto list.
     *
     * @param countryCreationDtos the country creation dtos
     */
    public CountryCreationDtoList(final List<CountryCreationDto> countryCreationDtos) {
        super(countryCreationDtos);
    }

    /**
     * Of.
     *
     * @param countryCreationDtos the country creation dtos
     * @return the country creation dto list
     */
    public static CountryCreationDtoList of(final List<CountryCreationDto> countryCreationDtos) {
        if (CollectionUtils.isEmpty(countryCreationDtos)) {
            return new CountryCreationDtoList();
        }
        return new CountryCreationDtoList(countryCreationDtos);
    }
}
