/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventshow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.apps.ticketeer.event.Event;
import com.xyz.apps.ticketeer.event.EventRepository;
import com.xyz.apps.ticketeer.eventvenue.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumRepository;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.EventVenue;
import com.xyz.apps.ticketeer.eventvenue.EventVenueRepository;


/**
 * The event show service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class EventShowService {

    /** The event show repository. */
    @Autowired
    private EventShowRepository eventShowRepository;

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The event repository. */
    @Autowired
    private EventRepository eventRepository;

    /** The event venue repository. */
    @Autowired
    private EventVenueRepository eventVenueRepository;

    /** The auditorium repository. */
    @Autowired
    private AuditoriumRepository auditoriumRepository;

    /** The auditorium seat repository. */
    @Autowired
    private AuditoriumSeatRepository auditoriumSeatRepository;

    /**
     * Adds the event show.
     *
     * @param eventShowDetailsDto the event show details dto
     * @return the event show
     */
    public EventShow add(final EventShowDetailsDto eventShowDetailsDto) {

        Objects.requireNonNull(eventShowDetailsDto, "The event show details cannot be null.");
        final EventShow eventShow = eventShowRepository.save(toEventShow(eventShowDetailsDto));

        if (eventShow != null) {
            eventShowSeatRepository.saveAll(
                eventShowDetailsDto.getSeatRowPriceDtoList()
                    .getSeatRowPriceDtos()
                    .stream()
                    .map(seatRowPriceDto -> toEventShowSeatList(seatRowPriceDto, eventShow))
                    .flatMap(List::stream)
                    .toList());
        }

        return eventShow;
    }

    /**
     * To event show seat list.
     *
     * @param seatRowPriceDto the seat row price dto
     * @param eventShow the event show
     * @return the list
     */
    private List<EventShowSeat> toEventShowSeatList(final SeatRowPriceDto seatRowPriceDto,
            final EventShow eventShow) {

        final List<Character> seatRows = new ArrayList<>();
        for (char row = seatRowPriceDto.getStartRow(); row <= seatRowPriceDto.getEndRow(); row++) {
            seatRows.add(row);
        }

        final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.findBySeatRowIn(seatRows);

        return
           (CollectionUtils.isNotEmpty(auditoriumSeats))
           ?
            auditoriumSeats
            .stream()
            .map(auditoriumSeat -> new EventShowSeat(seatRowPriceDto.getAmount(),
                SeatReservationStatus.AVAILABLE,
                eventShow,
                auditoriumSeat,
                null))
            .toList()
            : null;
    }

    /**
     * To event show.
     *
     * @param eventShowDetailsDto the event show details dto
     * @return the event show
     */
    private EventShow toEventShow(final EventShowDetailsDto eventShowDetailsDto) {

        final Event event = eventRepository.getById(eventShowDetailsDto.getEventId());
        final EventVenue eventVenue = eventVenueRepository.getById(eventShowDetailsDto.getEventVenueId());
        final Auditorium auditorium = auditoriumRepository.getById(eventShowDetailsDto.getAuditoriumId());

        return new EventShow(
            LocalDate.parse(eventShowDetailsDto.getDate()),
            LocalTime.parse(eventShowDetailsDto.getStartTime()),
            LocalTime.parse(eventShowDetailsDto.getEndTime()),
            event, eventVenue, auditorium);
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the list
     */
    public List<EventShow> search(final EventShowSearchCriteria eventShowSearchCriteria) {
        return eventShowRepository.findByEventShowSearchCriteria(
            eventShowSearchCriteria.getCityId(),
            eventShowSearchCriteria.getEventId(),
            LocalDate.parse(eventShowSearchCriteria.getDate()));
    }
}
