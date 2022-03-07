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
import javax.persistence.SequenceGenerator;
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
public class AuditoriumSeat extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seat_seq")
    @SequenceGenerator(initialValue = 1, name = "auditorium_seat_seq", allocationSize = 1)
    private Long id;

    /** The seat row. */
    @Column(nullable = false)
    @NotNull(message = "Seat row cannot be null.")
    private Character seatRow;

    /** The seat number. */
    @Column(nullable = false)
    @NotNull(message = "The seat number cannot be null.")
    @Min(value = 1, message = "Seat numbers must start with at least 1.")
    private Integer seatNumber;

    /** The auditorium. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumId", nullable = false)
    private Auditorium auditorium;

    public AuditoriumSeat() {

    }

    /**
     * Instantiates a new auditorium seat.
     *
     * @param seatRow the seat row
     * @param seatNumber the seat number
     * @param auditorium the auditorium
     */
    public AuditoriumSeat(final Character seatRow, final Integer seatNumber, final Auditorium auditorium) {

        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.auditorium = auditorium;
    }

    /**
     * Id.
     *
     * @param id the id
     * @return the auditorium seat
     */
    public AuditoriumSeat id(final Long id) {
        this.id = id;
        return this;
    }

}
