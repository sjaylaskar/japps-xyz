/*
* Id: EventShowDetailsDto.java 02-Mar-2022 3:06:56 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.SeatRowPriceDtoList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The EventShowDetailsDto.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class EventShowDetailsDto {

    /** The event id. */
    private Long eventId;

    /** The event venue id. */
    private Long eventVenueId;

    /** The auditorium id. */
    private Long auditoriumId;

    private Long cityId;

    /** The date. */
    private String date;

    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;

    /** The seat row price dto list. */
    private SeatRowPriceDtoList seatRowPriceDtoList = new SeatRowPriceDtoList();

    /**
     * To event show dto.
     *
     * @return the event show dto
     */
    public EventShowDto toEventShowDto() {
        final EventShowDto eventShowDto = new EventShowDto();
        eventShowDto.setCityId(cityId);
        eventShowDto.setEventId(eventId);
        eventShowDto.setEventVenueId(eventVenueId);
        eventShowDto.setAuditoriumId(auditoriumId);
        eventShowDto.setDate(date);
        eventShowDto.setStartTime(startTime);
        eventShowDto.setEndTime(endTime);
        return eventShowDto;
    }
}
