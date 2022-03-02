/*
* Id: AuditoriumDetailsDtoList.java 02-Mar-2022 3:29:32 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The AuditoriumDetailsDtoList.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class AuditoriumDetailsDtoList {

    /** The auditorium details dtos. */
    private List<AuditoriumDetailsDto> auditoriumDetailsDtos = new ArrayList<>();

    /**
     * Instantiates a new auditorium details dto list.
     */
    public AuditoriumDetailsDtoList() {

    }

    /**
     * Instantiates a new auditorium details dto list.
     *
     * @param auditoriumDetailsDtos the auditorium details dtos
     */
    public AuditoriumDetailsDtoList(final List<AuditoriumDetailsDto> auditoriumDetailsDtos) {
        this.auditoriumDetailsDtos.addAll(auditoriumDetailsDtos);
    }
}
