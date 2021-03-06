/*
 * Id: Show.java 15-Feb-2022 3:06:09 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
@Validated
public class EventShow extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_show_seq")
    @SequenceGenerator(initialValue = 1, name = "event_show_seq", allocationSize = 1)
    private Long id;

    /** The date. */
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "Show date cannot be null.")
    private LocalDate date;

    /** The start time. */
    @Column(nullable = false, columnDefinition = "TIME")
    @NotNull(message = "Show start time cannot be null.")
    private LocalTime startTime;

    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "Show end date cannot be null.")
    private LocalDate endDate;

    /** The end time. */
    @Column(nullable = false, columnDefinition = "TIME")
    @NotNull(message = "Show end time cannot be null.")
    private LocalTime endTime;

    /** The event. */
    @Column(nullable = false)
    @NotNull(message = "Event id cannot be null.")
    private Long eventId;

    /** The city id. */
    @Column(nullable = false)
    @NotNull(message = "City id cannot be null.")
    private Long cityId;

    /** The auditorium. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoriumId", nullable = false, foreignKey = @ForeignKey(name = "FK_EventShow_Auditorium"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Auditorium cannot be null for event show.")
    private Auditorium auditorium;

    /**
     * Id.
     *
     * @param id the id
     * @return the event show
     */
    public EventShow id(final Long id) {
        this.id = id;
        return this;
    }

}
