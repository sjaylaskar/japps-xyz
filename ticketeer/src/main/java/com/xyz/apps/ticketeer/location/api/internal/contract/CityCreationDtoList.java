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
 * The city creation dto list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCreationDtoList extends DtoList<CityCreationDto> {

    /**
     * Instantiates a new city creation dto list.
     */
    CityCreationDtoList() {

    }

    /**
     * Instantiates a new city creation dto list.
     *
     * @param cityCreationDtos the city creation dtos
     */
    CityCreationDtoList(final List<CityCreationDto> cityCreationDtos) {
        super(cityCreationDtos);
    }

    /**
     * Of.
     *
     * @param cityCreationDtos the city creation dtos
     * @return the city creation dto list
     */
    public static CityCreationDtoList of(final List<CityCreationDto> cityCreationDtos) {
        if (CollectionUtils.isEmpty(cityCreationDtos)) {
            return of();
        }
        return new CityCreationDtoList(cityCreationDtos);
    }

    /**
     * Of.
     *
     * @return the city creation dto list
     */
    public static CityCreationDtoList of() {
        return new CityCreationDtoList();
    }
}
