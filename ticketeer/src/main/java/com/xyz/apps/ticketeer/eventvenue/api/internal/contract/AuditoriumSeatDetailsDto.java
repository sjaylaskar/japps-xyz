/*
* Id: AuditoriumSeatDetailsDto.java 09-Mar-2022 4:00:47 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeat;
import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The auditorium seat details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatDetailsDto extends Dto<AuditoriumSeat> {
    /** The id. */
    private Long id;

    /** The seat row. */
    private Character seatRow;

    /** The seat number. */
    private Integer seatNumber;

    /** The auditorium. */
    private Long auditoriumId;
}
