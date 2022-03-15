/*
* Id: AuditoriumCreationDto.java 06-Mar-2022 10:27:38 pm SubhajoyLaskar
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
 * The auditorium seats creation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatsCreationDto {

    /** The event venue. */
    private Long eventVenueId;

    /** The auditorium name. */
    private String auditoriumName;

    /** The auditorium seat rows. */
    private List<AuditoriumSeatRowCreationDto> auditoriumSeatRows = new ArrayList<>();
}
