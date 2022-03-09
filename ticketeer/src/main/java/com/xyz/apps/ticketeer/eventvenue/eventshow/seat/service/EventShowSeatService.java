/*
 * Id: EventShowSeatService.java 06-Mar-2022 4:58:33 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowWithAuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatDetailsDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatNumbersDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeatModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowServiceException;
import com.xyz.apps.ticketeer.eventvenue.service.EventVenueService;
import com.xyz.apps.ticketeer.general.service.GeneralService;


/**
 * The event show seat service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventShowSeatService extends GeneralService {

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The event show seat model mapper. */
    @Autowired
    private EventShowSeatModelMapper eventShowSeatModelMapper;

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

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
     * Finds the event show seat details by event show id.
     *
     * @param eventShowWithAuditoriumDto the event show with auditorium dto
     * @return the event show seat details dto list
     */
    public EventShowSeatDetailsDtoList findEventShowSeatDetailsByEventShowAndAuditoriumId(@NotNull(
        message = "The event show details cannot be null."
    ) final EventShowWithAuditoriumDto eventShowWithAuditoriumDto) {

        final List<EventShowSeat> eventShowSeats = eventShowSeatRepository.findByEventShowId(eventShowWithAuditoriumDto
            .getEventShowId());

        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw new EventShowServiceException("No seats found for event show: " + eventShowWithAuditoriumDto);
        }

        final AuditoriumSeatDtoList auditoriumSeatDtoList = eventVenueService.findAuditoriumSeatsByAuditoriumId(
            eventShowWithAuditoriumDto
                .getAuditoriumId());

        if (auditoriumSeatDtoList == null || CollectionUtils.isNotEmpty(auditoriumSeatDtoList.getAuditoriumSeatDtos())) {
            throw new EventShowServiceException("No seats found for event show: " + eventShowWithAuditoriumDto);
        }

        final Map<Long, String> auditoriumIdToSeatNumberMap = auditoriumSeatDtoList.getAuditoriumSeatDtos().stream().collect(
            Collectors
                .toMap(AuditoriumSeatDto::getId, AuditoriumSeatDto::getSeatNumber));

        return EventShowSeatDetailsDtoList.of(eventShowWithAuditoriumDto.getEventShowId(), eventShowWithAuditoriumDto
            .getAuditoriumId(), auditoriumSeatDtoList.getAuditoriumName(), eventShowSeats.stream().map(
                eventShowSeat -> EventShowSeatDetailsDto.of(eventShowSeat.getId(), eventShowSeat.getAmount(), eventShowSeat
                    .getSeatReservationStatus().name(), auditoriumIdToSeatNumberMap.get(eventShowSeat.getAuditoriumSeat()
                        .getId()))).toList());

    }

    /**
     * Finds the seat numbers by ids.
     *
     * @param eventShowSeatIds the event show seat ids
     * @return the seat numbers by ids.
     */
    public EventShowSeatNumbersDto findSeatNumbersByIds(@NotEmpty(
        message = "The event show seat ids cannot be null or empty."
    ) final Set<Long> eventShowSeatIds) {

        final List<EventShowSeat> eventShowSeats = findAllEventShowSeatsByIds(eventShowSeatIds);
        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw new EventShowSeatsNotFoundException(eventShowSeatIds);
        }
        if (eventShowSeats.size() != eventShowSeatIds.size()) {
            final Set<Long> eventShowSeatIdsFound = eventShowSeats.stream().map(EventShowSeat::getId).collect(Collectors
                .toSet());
            throw new EventShowSeatsNotFoundException(eventShowSeatIds.stream().filter(
                eventShowSeatId -> !eventShowSeatIdsFound.contains(eventShowSeatId)).collect(Collectors.toSet()));
        }

        return EventShowSeatNumbersDto.of(eventVenueService.findSeatNumbersByAuditoriumSeatIds(eventShowSeats.stream().map(eventShowSeat -> eventShowSeat
            .getAuditoriumSeat().getId()).toList()));
    }

    /**
     * Finds the all event show seats by ids.
     *
     * @param seatIds the seat ids
     * @return the event show seats
     */
    private List<EventShowSeat> findAllEventShowSeatsByIds(final Set<Long> seatIds) {

        return eventShowSeatRepository.findAllById(seatIds);
    }

    /**
     * Calculate seats total amount.
     *
     * @param eventShowSeatIds the event show seat ids
     * @return the amount
     */
    public Double calculateSeatsTotalAmount(@NotEmpty(
        message = "The event show seat ids cannot be null or empty."
    ) final Set<Long> eventShowSeatIds) {

        if (CollectionUtils.isNotEmpty(eventShowSeatIds)) {
            final List<EventShowSeat> eventShowSeats = findAllEventShowSeatsByIds(eventShowSeatIds);
            if (CollectionUtils.isNotEmpty(eventShowSeats)) {
                if (eventShowSeats.size() != eventShowSeatIds.size()) {
                    final Set<Long> eventShowSeatIdsFound = eventShowSeats.stream().map(EventShowSeat::getId).collect(Collectors
                        .toSet());
                    throw new EventShowSeatsNotFoundException(eventShowSeatIds.stream().filter(
                        eventShowSeatId -> !eventShowSeatIdsFound.contains(eventShowSeatId)).collect(Collectors.toSet()));
                }
                return eventShowSeatRepository.findTotalAmount(eventShowSeatIds);
            }
        }
        return 0d;
    }

    /**
     * Are seats available.
     *
     * @param seatIds the seat ids
     * @return true, if successful
     */
    public boolean areSeatsAvailable(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        return eventShowSeatRepository.areSeatsAvailable(seatIds, seatIds.size());
    }

    /**
     * Reserve seats.
     *
     * @param seatIds the seat ids
     * @return the number of reserved seats.
     */
    @Transactional(rollbackFor = {Throwable.class})
    public int reserveSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        return eventShowSeatRepository.reserveSeats(seatIds, seatIds.size());
    }

    /**
     * Are seats reserved.
     *
     * @param seatIds the seat ids
     * @param bookingId the booking id
     * @return true, if successful
     */
    public boolean areSeatsReserved(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(
        message = "The booking id cannot be null."
    ) final Long bookingId) {

        return eventShowSeatRepository.areSeatsReserved(seatIds, bookingId, seatIds.size());
    }

    /**
     * Book seats.
     *
     * @param seatIds the seat ids
     * @param bookingId the booking id
     * @return the number of booked seats.
     */
    @Transactional(rollbackFor = {Throwable.class})
    public int bookSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(
        message = "The booking id cannot be null."
    ) final Long bookingId) {

        return eventShowSeatRepository.bookSeats(seatIds, seatIds.size(), bookingId);
    }

    /**
     * Fill booking for reserved seats.
     *
     * @param seatIds the seat ids
     * @param bookingId the booking id
     * @return the number of reserved seats.
     */
    @Transactional(rollbackFor = {Throwable.class})
    public int fillBookingForReservedSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(
        message = "The booking id cannot be null."
    ) final Long bookingId) {

        return eventShowSeatRepository.fillBookingForReservedSeats(seatIds, seatIds.size(), bookingId);
    }

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void cancelByBookingId(@NotNull(message = "The booking id cannot be null.") final Long bookingId) {

        eventShowSeatRepository.cancelByBookingId(bookingId);
    }

    /**
     * Unreserve seats.
     *
     * @param seatIds the seat ids
     * @return the number of seats unreserved
     */
    @Transactional(rollbackFor = {Throwable.class})
    public int unreserveSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {

        return eventShowSeatRepository.unreserveSeats(seatIds);
    }
}
