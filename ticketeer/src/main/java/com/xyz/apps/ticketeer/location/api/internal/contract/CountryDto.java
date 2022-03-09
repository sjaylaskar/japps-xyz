/*
* Id: CountryDTO.java 14-Feb-2022 3:13:33 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.Dto;
import com.xyz.apps.ticketeer.location.model.Country;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The country data transfer object.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto extends Dto<Country> {

    /** The id. */
    private Long id;

    /** The code. */
    private String code;

    /** The name. */
    private String name;
}
