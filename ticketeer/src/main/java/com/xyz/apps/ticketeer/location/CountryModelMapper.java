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
public class CountryModelMapper extends AbstractModelMapper<Country, CountryDto> {

    public CountryModelMapper() {

        super(Country.class, CountryDto.class);
    }
}
