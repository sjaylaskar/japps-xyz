/*
* Id: CountryDTO.java 14-Feb-2022 3:13:33 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import com.xyz.apps.ticketeer.general.model.Dto;

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
public class CountryDto extends Dto<Country> {

    /** The code. */
    private String code;

    /** The name. */
    private String name;
}
