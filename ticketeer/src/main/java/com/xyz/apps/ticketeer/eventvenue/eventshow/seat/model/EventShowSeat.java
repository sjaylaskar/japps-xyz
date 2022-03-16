/*
* Id: EventShowSeat.java 15-Feb-2022 3:17:36 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show seat.
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
       {@UniqueConstraint(name = "UNIQUE_eventshow_row_seat", columnNames = {"eventShow", "rowName", "seatNumber"}),
        @UniqueConstraint(name = "UNIQUE_eventshow_seatnumber", columnNames = {"eventShow", "seatNumber"})}
)
public class EventShowSeat extends com.xyz.apps.ticketeer.general.model.Entity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_show_seat_seq")
    @SequenceGenerator(initialValue = 1, name = "event_show_seat_seq", allocationSize = 1)
    private Long id;

    /** The amount. */
    @Column(nullable = false)
    @NotNull(message = "Seat price cannot be null.")
    private Double amount;

    /** The seat status. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Seat reservation status cannot be null.")
    private SeatReservationStatus seatReservationStatus = SeatReservationStatus.AVAILABLE;

    /** The event show. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "eventShowId", nullable = false, foreignKey = @ForeignKey(name = "FK_EventShow"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventShow eventShow;

    /** The row name. */
    @Column(nullable = false, length = 5)
    @NotBlank(message = "Row name cannot be blank.")
    @Size(min = 1, max = 5, message = "The row name must be of at least 1 and at most 5 characters.")
    private String rowName;

    /** The seat number. */
    @Column(nullable = false)
    @NotBlank(message = "The seat number cannot be blank.")
    private String seatNumber;

    /** The reservation time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime reservationTime;

    /** The booking time. */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingTime;

    /** The booking reservation id. */
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID bookingReservationId;
}
