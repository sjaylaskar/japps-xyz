/*
 * Id: Booking.java 15-Feb-2022 3:14:35 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    @NotNull(message = "The booking id cannot be null.")
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
    @NotNull(message = "The event show time cannot be null.")
    private LocalTime eventShowTime;

    /** The event venue name. */
    @NonNull
    @NotBlank(message = "The event venue name cannot be null.")
    private String eventVenueName;

    @NonNull
    @NotBlank(message = "The event venue auditorium name cannot be null.")
    private String auditoriumName;

    /** The city name. */
    @NonNull
    @NotBlank(message = "The city name cannot be null.")
    private String cityName;

    /** The seats. */
    @NonNull
    @NotEmpty(message = "Seat numbers must not be empty.")
    private Set<String> seatNumbers = new TreeSet<>();

    @NonNull
    @NotEmpty(message = "Seat base amounts must not be empty.")
    private List<Double> seatBaseAmounts = new ArrayList<>();
}
