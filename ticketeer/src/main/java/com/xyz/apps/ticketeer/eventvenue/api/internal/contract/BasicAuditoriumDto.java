/*
* Id: BasicAuditoriumDto.java 15-Mar-2022 3:06:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The basic auditorium dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicAuditoriumDto {

    /** The auditorium id. */
    private Long auditoriumId;

    /** The name. */
    private String name;

    /**
     * Of.
     *
     * @param auditoriumId the auditorium id
     * @param name the name
     * @return the basic auditorium dto
     */
    public static BasicAuditoriumDto of(final Long auditoriumId, final String name) {
        final BasicAuditoriumDto basicAuditoriumDto = new BasicAuditoriumDto();
        basicAuditoriumDto.setAuditoriumId(auditoriumId);
        basicAuditoriumDto.setName(name);
        return basicAuditoriumDto;
    }
}
