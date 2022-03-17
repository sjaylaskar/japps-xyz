/*
* Id: Auditorium.java 15-Feb-2022 3:04:24 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Auditorium.
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
       {@UniqueConstraint(name = "UNIQUE_eventvenue_name", columnNames = {"eventVenueId", "name"})}
)
public class Auditorium extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    @SequenceGenerator(initialValue = 1, name = "auditorium_seq", allocationSize = 1)
    private Long id;

    /** The name. */
    @Column(nullable = false)
    @NotBlank(message = "Auditorium name is required.")
    private String name;

    /** The event venue. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventVenueId", nullable = false, foreignKey = @ForeignKey(name = "FK_EventVenue"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Event venue is required for Auditorium.")
    private EventVenue eventVenue;

    /**
     * Id.
     *
     * @param id the id
     * @return the auditorium
     */
    public Auditorium id(final Long id) {
        this.id = id;
        return this;
    }

    /**
     * Name.
     *
     * @param name the name
     * @return the auditorium
     */
    public Auditorium name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Event venue.
     *
     * @param eventVenueId the event venue id
     * @return the auditorium
     */
    public Auditorium eventVenue(final Long eventVenueId) {
        eventVenue = new EventVenue().id(eventVenueId);
        return this;
    }
}
