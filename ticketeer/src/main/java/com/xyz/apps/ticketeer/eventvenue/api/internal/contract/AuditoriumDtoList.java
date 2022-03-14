/*
 * Id: AuditoriumDtoList.java 15-Mar-2022 3:47:42 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;


/**
 * The auditorium dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class AuditoriumDtoList extends DtoList<AuditoriumDto> {

    /**
     * Instantiates a new auditorium dto list.
     */
    public AuditoriumDtoList() {

    }

    /**
     * Instantiates a new auditorium dto list.
     *
     * @param auditoriumDtos the auditorium dtos
     */
    public AuditoriumDtoList(final List<AuditoriumDto> auditoriumDtos) {

        super(auditoriumDtos);
    }

    /**
     * Of.
     *
     * @param auditoriumDtos the auditorium dtos
     * @return the auditorium dto list
     */
    public static AuditoriumDtoList of(final List<AuditoriumDto> auditoriumDtos) {

        if (CollectionUtils.isEmpty(auditoriumDtos)) {
            return new AuditoriumDtoList();
        }
        return new AuditoriumDtoList(auditoriumDtos);
    }

}
