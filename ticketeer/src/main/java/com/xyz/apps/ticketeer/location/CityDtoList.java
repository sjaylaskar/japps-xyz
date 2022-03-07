/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;

/**
 * The city dto list.
 */
public class CityDtoList extends DtoList<CityDto> {

    /**
     * Instantiates a new city dto list.
     */
    CityDtoList() {

    }

    /**
     * Instantiates a new city dto list.
     *
     * @param cityDtos the city dtos
     */
    CityDtoList(final List<CityDto> cityDtos) {
        super(cityDtos);
    }

    /**
     * Of.
     *
     * @param cityDtos the city dtos
     * @return the city dto list
     */
    static CityDtoList of(final List<CityDto> cityDtos) {
        if (CollectionUtils.isEmpty(cityDtos)) {
            return of();
        }
        return new CityDtoList(cityDtos);
    }

    static CityDtoList of() {
        return new CityDtoList();
    }
}
