/*
* Id: AuditoriumDetailsDtoList.java 02-Mar-2022 3:29:32 pm SubhajoyLaskar
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
 * The auditorium seat row dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatRowDto {

    /** The row name. */
    private String rowName;

    /** The seats. */
    private List<AuditoriumSeatDto> seats = new ArrayList<>();

    /**
     * Of.
     *
     * @param rowName the row name
     * @param seats the seats
     * @return the auditorium seat row dto
     */
    public static AuditoriumSeatRowDto of(final String rowName, final List<AuditoriumSeatDto> seats) {
        final AuditoriumSeatRowDto auditoriumSeatRowDto = new AuditoriumSeatRowDto();
        auditoriumSeatRowDto.setRowName(rowName);
        auditoriumSeatRowDto.setSeats(seats);
        return auditoriumSeatRowDto;
    }
}
