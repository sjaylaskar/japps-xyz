/*
* Id: EventDetails.java 03-Mar-2022 6:26:07 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.validation.annotation.Validated;

import com.mongodb.lang.NonNull;
import com.xyz.apps.ticketeer.event.resources.Messages;
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
    private ObjectId id;

    /** The event id. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_ID)
    private Long eventId;

    /** The name. */
    @NonNull
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_NAME)
    @TextIndexed(weight = 5)
    private String name;

    /** The description. */
    @TextIndexed(weight = 4)
    private String description;

    /** The event type. */
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_TYPE)
    @NonNull
    private String eventType;

    /** The language. */
    @TextIndexed(weight = 2)
    @NonNull
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_LANGUAGE)
    private String language;

    /** The duration. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_DURATION)
    @Min(value = 5, message = Messages.MESSAGE_ERROR_INVALID_DURATION)
    private Long durationInMinutes;

    /** The release date. */
    @NonNull
    @NotNull(message = Messages.MESSAGE_ERROR_REQUIRED_RELEASE_DATE)
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
    @NonNull
    @NotBlank(message = Messages.MESSAGE_ERROR_REQUIRED_COUNTRY_NAME)
    @TextIndexed(weight = 1.74f)
    private String countryName;

    /** The score. */
    @TextScore
    private Float score;
}
