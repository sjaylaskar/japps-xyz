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
 * The country dto list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDtoList extends DtoList<CountryDto> {

    /**
     * Instantiates a new country dto list.
     */
    public CountryDtoList() {

    }

    /**
     * Instantiates a new country dto list.
     *
     * @param countryDtos the country dtos
     */
    public CountryDtoList(final List<CountryDto> countryDtos) {
        super(countryDtos);
    }

    /**
     * Of.
     *
     * @param countryDtos the country dtos
     * @return the country dto list
     */
    public static CountryDtoList of(final List<CountryDto> countryDtos) {
        if (CollectionUtils.isEmpty(countryDtos)) {
            return new CountryDtoList();
        }
        return new CountryDtoList(countryDtos);
    }
}
