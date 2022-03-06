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
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.event.Event;
import com.xyz.apps.ticketeer.event.EventDetailsDtoList;
import com.xyz.apps.ticketeer.event.EventRepository;
import com.xyz.apps.ticketeer.event.EventService;
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
@Validated
@Service
public class EventShowService {

    /** The event show repository. */
    @Autowired
    private EventShowRepository eventShowRepository;

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The event service. */
    @Autowired
    private EventService eventService;

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

    /** The event show seat model mapper. */
    @Autowired
    private EventShowSeatModelMapper eventShowSeatModelMapper;

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
     * Delete.
     *
     * @param id the id
     */
    public void delete(final Long id) {

        Objects.requireNonNull(id, "The event show id cannot be null.");

        if (eventShowRepository.existsById(id)) {
            eventShowRepository.deleteById(id);
        }

        throw new EventShowNotFoundException(id);
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

        return (CollectionUtils.isNotEmpty(auditoriumSeats))
            ? auditoriumSeats
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

    /**
     * Search events by city.
     *
     * @param cityId the city id
     * @return the event details dto list
     */
    public EventDetailsDtoList searchEventsByCity(final Long cityId) {

        final List<Event> events = eventShowRepository.findEventsByCityId(cityId);
        return (CollectionUtils.isNotEmpty(events)) ? eventService.findAllByEvents(events) : null;
    }

    /**
     * Finds the event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seat dto list
     */
    public EventShowSeatDtoList findEventShowSeatsByEventShowId(final Long eventShowId) {
        return EventShowSeatDtoList.of(eventShowSeatModelMapper.toDtos(eventShowSeatRepository.findByEventShowId(eventShowId)));
    }

    /**
     * Finds the all event show seats by ids.
     *
     * @param seatIds the seat ids
     * @return the event show seats
     */
    public List<EventShowSeat> findAllEventShowSeatsByIds(final Set<Long> seatIds) {
        return eventShowSeatRepository.findAllById(seatIds);
    }

    /**
     * Calculate seats total amount.
     *
     * @param eventShowSeatIds the event show seat ids
     * @return the amount
     */
    public Double calculateSeatsTotalAmount(@NotEmpty(message = "The event show seat ids cannot be null or empty.") final Set<Long> eventShowSeatIds) {
        if (CollectionUtils.isNotEmpty(eventShowSeatIds)) {
            final List<EventShowSeat> eventShowSeats = findAllEventShowSeatsByIds(eventShowSeatIds);
            if (CollectionUtils.isNotEmpty(eventShowSeats)) {
                if (eventShowSeats.size() != eventShowSeatIds.size()) {
                    final Set<Long> eventShowSeatIdsFound = eventShowSeats.stream().map(EventShowSeat::getId).collect(Collectors.toSet());
                    throw new EventShowSeatsNotFoundException(eventShowSeatIds.stream().filter(eventShowSeatId -> !eventShowSeatIdsFound.contains(eventShowSeatId)).collect(Collectors.toSet()));
                }
                return eventShowSeatRepository.findTotalAmount(eventShowSeatIds);
            }
        }
        return 0d;
    }
}
