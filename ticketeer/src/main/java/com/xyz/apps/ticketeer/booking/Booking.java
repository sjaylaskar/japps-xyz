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

import com.xyz.apps.ticketeer.eventshow.EventShow;
import com.xyz.apps.ticketeer.user.User;

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
public class Booking extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The booking time. */
    @Column(nullable = false)
    private LocalDateTime bookingTime;

    /** The booking status. */
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    /** The number of seats. */
    private int numberOfSeats;

    /** The user. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private User user;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false, updatable = false)
    private EventShow eventShow;
}
