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
 * The auditorium creation dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditoriumCreationDtoList {

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium names. */
    private List<String> auditoriumNames = new ArrayList<>();
}
