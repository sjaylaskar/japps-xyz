/*
* Id: AuditoriumSeatsDto.java 15-Mar-2022 9:54:05 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The auditorium seats dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatsDto {

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium name. */
    private String auditoriumName;

    /** The auditorium seat rows. */
    private List<AuditoriumSeatRowDto> auditoriumSeatRows = new ArrayList<>();
}
