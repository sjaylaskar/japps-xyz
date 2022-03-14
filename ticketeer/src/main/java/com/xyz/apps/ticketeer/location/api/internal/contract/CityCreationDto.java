/*
 * Id: City.java 13-Feb-2022 5:34:55 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.location.model.City;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The city creation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCreationDto extends Dto<City> {

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    /** The country. */
    private Long countryId;
}
