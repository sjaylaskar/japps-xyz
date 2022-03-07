/*
* Id: EventDetails.java 03-Mar-2022 6:26:07 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.model.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event details.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Document("eventDetails")
@Getter
@Setter
@ToString
@Validated
public class EventDetails extends Entity {

    /** The id. */
    @MongoId
    private Long id;

    /** The event id. */
    @NotBlank(message = "The event id cannot be blank.")
    private Long eventId;

    /** The name. */
    @TextIndexed(weight = 5)
    @NotBlank(message = "The event name cannot be blank.")
    private String name;

    /** The description. */
    @TextIndexed(weight = 4)
    private String description;

    /** The event type. */
    @NotBlank(message = "The event type cannot be blank.")
    private String eventType;

    /** The language. */
    @TextIndexed(weight = 2)
    private String language;

    /** The duration. */
    private long duration;

    /** The release date. */
    private LocalDate releaseDate;

    /** The genre. */
    @TextIndexed(weight = 3)
    private String genre;

    /** The movie director name. */
    @TextIndexed(weight = 1.3f)
    private String movieDirectorName;

    /** The movie cast names. */
    @TextIndexed(weight = 1.5f)
    private List<String> movieCastNames;

    /** The country name. */
    @TextIndexed(weight = 1.74f)
    private String countryName;

    /** The score. */
    @TextScore
    private Float score;
}
