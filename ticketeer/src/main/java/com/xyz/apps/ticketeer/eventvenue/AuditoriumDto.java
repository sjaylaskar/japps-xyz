/*
* Id: AuditoriumDto.java 06-Mar-2022 10:27:38 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The auditorium dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class AuditoriumDto extends Dto<Auditorium> {

    /** The id. */
    private Long id;

    /** The name. */
    private String name;

    /** The number of seats. */
    private Integer numberOfSeats = 1;

    /** The event venue. */
    private Long eventVenueId;
}
