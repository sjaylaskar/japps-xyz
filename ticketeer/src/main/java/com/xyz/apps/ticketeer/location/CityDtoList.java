/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.model.DtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The city dto list.
 */
@Getter
@Setter
@ToString
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
            return new CityDtoList();
        }
        return new CityDtoList(cityDtos);
    }
}
