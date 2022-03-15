/*
* Id: AuditoriumSeat.java 15-Feb-2022 3:16:29 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

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
@Validated
@Table(
    uniqueConstraints =
       {@UniqueConstraint(name = "UNIQUE_audi_rowName_seatNr", columnNames = {"auditorium", "rowName", "seatNumber"}),
        @UniqueConstraint(name = "UNIQUE_audi_seatNumber", columnNames = {"auditorium", "seatNumber"})}
)
public class AuditoriumSeat extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seat_seq")
    @SequenceGenerator(initialValue = 1, name = "auditorium_seat_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 5)
    @NotBlank(message = "Row name cannot be blank.")
    @Size(min = 1, max = 5, message = "The row name must be of at least 1 and at most 5 characters.")
    private String rowName;

    /** The seat number. */
    @Column(nullable = false)
    @NotNull(message = "The seat number cannot be null.")
    @Min(value = 1, message = "The minimum seat number must be 1.")
    private Integer seatNumber;

    /** The auditorium. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Auditorium is required for Auditorium Seat.")
    private Auditorium auditorium;

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
