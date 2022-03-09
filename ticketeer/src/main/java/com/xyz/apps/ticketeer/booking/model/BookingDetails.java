/*
 * Id: Booking.java 15-Feb-2022 3:14:35 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import com.mongodb.lang.NonNull;
import com.xyz.apps.ticketeer.general.model.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking details.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Document("bookingDetails")
@Getter
@Setter
@ToString
@Validated
public class BookingDetails extends Entity {

    /** The id. */
    @MongoId
    private ObjectId id;

    /** The booking id. */
    @NonNull
    @NotBlank(message = "The event name cannot be null.")
    private Long bookingId;

    /** The event show name. */
    @NonNull
    @NotBlank(message = "The event name cannot be null.")
    private String eventName;

    /** The event show date. */
    @NonNull
    @NotNull(message = "The event show date cannot be null.")
    private LocalDate eventShowDate;

    /** The event show time. */
    @NonNull
    @NotNull(message = "The event show date cannot be null.")
    private LocalTime eventShowTime;

    /** The event venue name. */
    @NonNull
    @NotBlank(message = "The event venue name cannot be null.")
    private String eventVenueName;

    @NonNull
    @NotBlank(message = "The event venue auditorium name cannot be null.")
    private String auditoriumName;

    /** The seats. */
    @NonNull
    @NotEmpty(message = "Seats cannot be empty.")
    private List<String> seats;
}
