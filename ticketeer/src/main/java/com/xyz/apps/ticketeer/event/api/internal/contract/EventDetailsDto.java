/*
* Id: EventDetailsDto.java 03-Mar-2022 2:56:20 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.api.internal.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.event.model.EventDetails;
import com.xyz.apps.ticketeer.general.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The EventDetailsDto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetailsDto extends Dto<EventDetails> {

    /** The id. */
    private String id;

    /** The event id. */
    private Long eventId;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The event type. */
    private String eventType;

    /** The language. */
    private String language;

    /** The duration. */
    private long duration;

    /** The release date. */
    private String releaseDate;

    /** The genre. */
    private String genre;

    /** The movie director name. */
    private String movieDirectorName;

    /** The movie cast names. */
    private List<String> movieCastNames;

    /** The country name. */
    private String countryName;
}
