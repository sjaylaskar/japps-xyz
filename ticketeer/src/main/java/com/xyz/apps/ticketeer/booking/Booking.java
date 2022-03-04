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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.xyz.apps.ticketeer.eventshow.EventShow;
import com.xyz.apps.ticketeer.user.User;

import lombok.Builder;
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
@Builder
public class Booking extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
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
    @Builder.Default
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventShow eventShow;

    /** The phone number. */
    @Column(nullable = false, unique = true, length = 15)
    @Size(min = 10, max = 15, message = "The phone number must be be at least 10 and at most 15 characters.")
    @NotNull(message = "The phone number cannot be null.")
    private String phoneNumber;

    /**
     * Instantiates a new booking.
     */
    public Booking() {

    }

    /**
     * Instantiates a new booking.
     *
     * @param bookingTime the booking time
     * @param reservationTime the reservation time
     * @param bookingStatus the booking status
     * @param numberOfSeats the number of seats
     * @param amount the amount
     * @param finalAmount the final amount
     * @param offerCode the offer code
     * @param user the user
     * @param eventShow the event show
     */
    public Booking(final LocalDateTime bookingTime,
                   final LocalDateTime reservationTime,
                   final BookingStatus bookingStatus,
                   final Integer numberOfSeats,
                   @NotNull(message = "Amount cannot be null.")
                   final Double amount,
                   @NotNull(message = "Amount cannot be null.")
                   final Double finalAmount,
                   final String offerCode,
                   final User user,
                   final EventShow eventShow) {

        this.bookingTime = bookingTime;
        this.reservationTime = reservationTime;
        this.bookingStatus = bookingStatus;
        this.numberOfSeats = numberOfSeats;
        this.amount = amount;
        this.finalAmount = finalAmount;
        this.offerCode = offerCode;
        this.user = user;
        this.eventShow = eventShow;
    }

    /**
     * Instantiates a new booking.
     *
     * @param id the id
     * @param bookingTime the booking time
     * @param reservationTime the reservation time
     * @param bookingStatus the booking status
     * @param numberOfSeats the number of seats
     * @param amount the amount
     * @param finalAmount the final amount
     * @param offerCode the offer code
     * @param user the user
     * @param eventShow the event show
     * @param phoneNumber the phone number
     */
    Booking(final Long id,
            final LocalDateTime bookingTime,
            final LocalDateTime reservationTime,
            final BookingStatus bookingStatus,
            final Integer numberOfSeats,
            final Double amount,
            final Double finalAmount,
            final String offerCode,
            final User user,
            final EventShow eventShow,
            final String phoneNumber) {

        this.id = id;
        this.bookingTime = bookingTime;
        this.reservationTime = reservationTime;
        this.bookingStatus = bookingStatus;
        this.numberOfSeats = numberOfSeats;
        this.amount = amount;
        this.finalAmount = finalAmount;
        this.offerCode = offerCode;
        this.user = user;
        this.eventShow = eventShow;
        this.phoneNumber = phoneNumber;
    }
}
