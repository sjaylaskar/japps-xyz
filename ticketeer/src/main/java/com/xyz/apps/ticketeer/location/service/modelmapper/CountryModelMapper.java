/*
 * Id: CountryDTOMapper.java 14-Feb-2022 6:37:19 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service.modelmapper;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDto;
import com.xyz.apps.ticketeer.location.model.Country;


/**
 * The country data transfer object mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CountryModelMapper extends GeneralModelMapper<Country, CountryDto> {

    public CountryModelMapper() {

        super(Country.class, CountryDto.class);
    }
}
