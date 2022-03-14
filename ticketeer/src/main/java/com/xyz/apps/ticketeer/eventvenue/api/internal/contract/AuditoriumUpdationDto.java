/*
* Id: AuditoriumUpdationDto.java 06-Mar-2022 10:27:38 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.api.internal.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The auditorium updation dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumUpdationDto extends Dto<Auditorium> {

    /** The id. */
    private Long id;

    /** The name. */
    private String name;
}
