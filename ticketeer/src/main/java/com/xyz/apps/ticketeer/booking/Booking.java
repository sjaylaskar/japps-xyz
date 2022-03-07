/*
 * Id: Booking.java 15-Feb-2022 3:14:35 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The Booking.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
@Validated
public class Booking extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
    @SequenceGenerator(initialValue = 1, name = "booking_seq", allocationSize = 1)
    private Long id;

    /** The booking time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingTime;

    /** The reservation time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime reservationTime;

    /** The booking status. */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    /** The number of seats. */
    @Column(nullable = false)
    @NotNull(message = "Number of seats cannot be null.")
    @Min(value = 0, message = "Number of seats must be at least 0.")
    private Integer numberOfSeats = 0;

    /** The amount. */
    @NotNull(message = "Amount cannot be null.")
    @Column(nullable = false)
    private Double amount;

    /** The final amount. */
    @NotNull(message = "Amount cannot be null.")
    @Column(nullable = false)
    private Double finalAmount;

    /** The offer code. */
    private String offerCode;

    /** The user. */
    @Column(nullable = false)
    @NotBlank(message = "The username cannot be null.")
    private String username;

    /** The event show. */
    @Column(nullable = false)
    @NotNull(message = "The event show id cannot be null.")
    private Long eventShowId;

    @Column(nullable = false)
    @NotNull(message = "The city id cannot be null.")
    private Long cityId;

    /** The phone number. */
    @Column(nullable = false, length = 15)
    @Size(min = 10, max = 15, message = "The phone number must be be at least 10 and at most 15 characters.")
    @NotBlank(message = "The phone number cannot be empty.")
    private String phoneNumber;

    @Column(nullable = false)
    @Email(message = "Invalid email id.")
    @NotBlank(message = "The email id cannot be empty.")
    private String emailId;

    /**
     * Id.
     *
     * @param id the id
     * @return the booking
     */
    public Booking id(final Long id) {
        this.id = id;
        return this;
    }
}
