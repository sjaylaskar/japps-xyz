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
 * The event details dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetailsDto extends Dto<EventDetails> {

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
    private Long durationInMinutes;

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

    /**
     * Of.
     *
     * @param eventDetailsCreationDto the event details creation dto
     * @return the event details dto
     */
    public static EventDetailsDto of(final EventDetailsCreationDto eventDetailsCreationDto) {
        final EventDetailsDto eventDetailsDto = new EventDetailsDto();
        eventDetailsDto.setName(eventDetailsCreationDto.getName());
        eventDetailsDto.setDescription(eventDetailsCreationDto.getDescription());
        eventDetailsDto.setEventType(eventDetailsCreationDto.getEventType());
        eventDetailsDto.setLanguage(eventDetailsCreationDto.getLanguage());
        eventDetailsDto.setDurationInMinutes(eventDetailsCreationDto.getDurationInMinutes());
        eventDetailsDto.setReleaseDate(eventDetailsCreationDto.getReleaseDate());
        eventDetailsDto.setGenre(eventDetailsCreationDto.getGenre());
        eventDetailsDto.setMovieDirectorName(eventDetailsCreationDto.getMovieDirectorName());
        eventDetailsDto.setMovieCastNames(eventDetailsCreationDto.getMovieCastNames());
        eventDetailsDto.setCountryName(eventDetailsCreationDto.getCountryName());
        return eventDetailsDto;
    }
}
