/*
 * Id: AuditoriumDtoList.java 15-Mar-2022 3:47:42 am SubhajoyLaskar
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
 * The auditorium dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumDtoList {

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditoriums. */
    private List<BasicAuditoriumDto> auditoriums = new ArrayList<>();

    /**
     * Of.
     *
     * @return the auditorium dto list
     */
    public static AuditoriumDtoList of() {
        return new AuditoriumDtoList();
    }

    /**
     * Of.
     *
     * @param eventVenueId the event venue id
     * @param auditoriums the auditoriums
     * @return the auditorium dto list
     */
    public static AuditoriumDtoList of(final Long eventVenueId, final List<BasicAuditoriumDto> auditoriums) {
        final AuditoriumDtoList auditoriumDtoList = AuditoriumDtoList.of();
        auditoriumDtoList.setEventVenueId(eventVenueId);
        auditoriumDtoList.setAuditoriums(auditoriums);
        return auditoriumDtoList;
    }
}
