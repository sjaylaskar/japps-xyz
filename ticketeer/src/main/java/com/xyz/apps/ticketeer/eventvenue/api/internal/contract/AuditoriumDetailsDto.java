/*
* Id: AuditoriumDetailsDto.java 02-Mar-2022 12:09:52 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The auditorium details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumDetailsDto {

    /** The name. */
    private String name;

    /** The number of rows. */
    private int numberOfRows;

    /** The seats per row. */
    private int seatsPerRow;
}
