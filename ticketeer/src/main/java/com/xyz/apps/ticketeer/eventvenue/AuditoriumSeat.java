/*
* Id: AuditoriumSeat.java 15-Feb-2022 3:16:29 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    private String row;

    /** The seat number. */
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
    public AuditoriumSeat(final String row, final int seatNumber, final Auditorium auditorium) {

        this.row = row;
        this.seatNumber = seatNumber;
        this.auditorium = auditorium;
    }
    
    
}
