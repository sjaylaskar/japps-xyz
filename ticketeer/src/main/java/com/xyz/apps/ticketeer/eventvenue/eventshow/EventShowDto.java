/*
* Id: EventShowDto.java 02-Mar-2022 5:45:41 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import java.time.LocalDate;
import java.time.LocalTime;

import com.xyz.apps.ticketeer.event.Event;
import com.xyz.apps.ticketeer.eventvenue.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.EventVenue;
import com.xyz.apps.ticketeer.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The event show dto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowDto extends Dto<EventShow> {

    /** The date. */
    private LocalDate date;

    /** The start time. */
    private LocalTime startTime;

    /** The end time. */
    private LocalTime endTime;

    /** The event venue. */
    private EventVenue eventVenue;

    /** The auditorium. */
    private Auditorium auditorium;

    /** The event. */
    private Event event;
}
