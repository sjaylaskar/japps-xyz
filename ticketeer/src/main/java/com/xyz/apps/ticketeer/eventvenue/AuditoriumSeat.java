/*
* Id: AuditoriumSeat.java 15-Feb-2022 3:16:29 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The AuditoriumSeat.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class AuditoriumSeat extends com.xyz.apps.ticketeer.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The row. */
    @Column(nullable = false)
    @NotNull(message = "Seat row cannot be null.")
    private Character row;

    /** The seat number. */
    @Min(value = 1, message = "Seat numbers must start with at least 1.")
    private int seatNumber;

    /** The auditorium. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumId", nullable = false, updatable = false)
    private Auditorium auditorium;

    /**
     * Instantiates a new auditorium seat.
     *
     * @param row the row
     * @param seatNumber the seat number
     * @param auditorium the auditorium
     */
    public AuditoriumSeat(final Character row, final int seatNumber, final Auditorium auditorium) {

        this.row = row;
        this.seatNumber = seatNumber;
        this.auditorium = auditorium;
    }


}
