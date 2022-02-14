/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.model.DtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The country dto list.
 */
@Getter
@Setter
@ToString
public class CountryDtoList extends DtoList<CountryDto> {

    /** The country dtos. */
    private final List<CountryDto> countryDtos = new ArrayList<>();

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
        this.countryDtos.addAll(countryDtos);
    }

    /**
     * Of.
     *
     * @param countryDtos the country dtos
     * @return the country dto list
     */
    static CountryDtoList of(final List<CountryDto> countryDtos) {
        if (CollectionUtils.isEmpty(countryDtos)) {
            return new CountryDtoList();
        }
        return new CountryDtoList(countryDtos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<CountryDto> dtos() {

        return countryDtos;
    }


}
