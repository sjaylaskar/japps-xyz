/*
* Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.model.general.AbstractModelMapper;

/**
 * The city model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class CityModelMapper extends AbstractModelMapper<City, CityDto> {

    public CityModelMapper() {
        super(City.class, CityDto.class);

        //initMappings();
    }

}
