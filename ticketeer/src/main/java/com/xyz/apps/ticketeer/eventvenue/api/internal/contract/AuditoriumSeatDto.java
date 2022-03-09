/*
* Id: AuditoriumDto.java 06-Mar-2022 10:27:38 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatDto {

    /** The id. */
    private Long id;

    /** The seat number. */
    private String seatNumber;

    /**
     * Of.
     *
     * @param id the id
     * @param seatRow the seat row
     * @param seatNumber the seat number
     * @return the auditorium seat dto
     */
    public static AuditoriumSeatDto of(final Long id, final Character seatRow, final Integer seatNumber) {
        final AuditoriumSeatDto auditoriumSeatDto = new AuditoriumSeatDto();
        auditoriumSeatDto.setId(id);
        auditoriumSeatDto.setSeatNumber(String.valueOf(seatRow) + StringUtils.SPACE + seatNumber);
        return auditoriumSeatDto;
    }
}
