/*
* Id: EventShowSeatService.java 06-Mar-2022 4:58:33 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * The event show seat service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventShowSeatService {

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The event show seat model mapper. */
    @Autowired
    private EventShowSeatModelMapper eventShowSeatModelMapper;

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
    private List<EventShowSeat> findAllEventShowSeatsByIds(final Set<Long> seatIds) {
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
    public boolean areSeatsReserved(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(message = "The booking id cannot be null.") final Long bookingId) {
        return eventShowSeatRepository.areSeatsReserved(seatIds, bookingId, seatIds.size());
    }

    /**
     * Book seats.
     *
     * @param seatIds the seat ids
     * @param bookingId the booking id
     * @return the number of booked seats.
     */
    public int bookSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(message = "The booking id cannot be null.") final Long bookingId) {
        return eventShowSeatRepository.bookSeats(seatIds, seatIds.size(), bookingId);
    }

    /**
     * Fill booking for reserved seats.
     *
     * @param seatIds the seat ids
     * @param bookingId the booking id
     * @return the number of reserved seats.
     */
    public int fillBookingForReservedSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds, @NotNull(message = "The booking id cannot be null.") final Long bookingId) {
        return eventShowSeatRepository.fillBookingForReservedSeats(seatIds, seatIds.size(), bookingId);
    }

    /**
     * Cancel by booking id.
     *
     * @param bookingId the booking id
     */
    public void cancelByBookingId(@NotNull(message = "The booking id cannot be null.") final Long bookingId) {
        eventShowSeatRepository.cancelByBookingId(bookingId);
    }

    /**
     * Unreserve seats.
     *
     * @param seatIds the seat ids
     * @return the number of seats unreserved
     */
    public int unreserveSeats(@NotEmpty(message = "The seat ids cannot be empty.") final Set<Long> seatIds) {
        return eventShowSeatRepository.unreserveSeats(seatIds, seatIds.size());
    }
}
