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
 * The auditorium seat dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumSeatDtoList {

    /** The auditorium id. */
    private Long auditoriumId;

    /** The auditorium name. */
    private String auditoriumName;

    /** The auditorium seat dtos. */
    private List<AuditoriumSeatDto> auditoriumSeatDtos = new ArrayList<>();

    /**
     * Instantiates a new auditorium seat dto list.
     */
    public AuditoriumSeatDtoList() {

    }

    /**
     * Instantiates a new auditorium seat dto list.
     *
     * @param auditoriumSeatDtos the auditorium seat dtos
     */
    public AuditoriumSeatDtoList(final List<AuditoriumSeatDto> auditoriumSeatDtos) {
        this.auditoriumSeatDtos.addAll(auditoriumSeatDtos);
    }

    /**
     * Of.
     *
     * @param auditoriumId the auditorium id
     * @param auditoriumName the auditorium name
     * @param auditoriumSeatDtos the auditorium seat dtos
     * @return the auditorium seat dto list
     */
    public static AuditoriumSeatDtoList of(final Long auditoriumId, final String auditoriumName, final List<AuditoriumSeatDto> auditoriumSeatDtos) {
        final AuditoriumSeatDtoList auditoriumSeatDtoList = new AuditoriumSeatDtoList(auditoriumSeatDtos);
        auditoriumSeatDtoList.setAuditoriumId(auditoriumId);
        auditoriumSeatDtoList.setAuditoriumName(auditoriumName);
        return auditoriumSeatDtoList;
    }
}
