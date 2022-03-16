/*
 * Id: EventShowSeatReservationService.java 06-Mar-2022 4:58:33 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatForShowResponseDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsBookingRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsBookingResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsCancellationRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsCancellationResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsReservationRequestDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsReservationResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatForShowModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatPricesModelMapper;
import com.xyz.apps.ticketeer.general.service.GeneralService;


/**
 * The event show seat reservation service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventShowSeatReservationService extends GeneralService {

    /** The event show seat service. */
    @Autowired
    private EventShowSeatService eventShowSeatService;

    /** The event show seat repository. */
    @Autowired
    private EventShowSeatRepository eventShowSeatRepository;

    /** The event show seat for show model mapper. */
    @Autowired
    private EventShowSeatForShowModelMapper eventShowSeatForShowModelMapper;

    /** The event show seat prices model mapper. */
    @Autowired
    private EventShowSeatPricesModelMapper eventShowSeatPricesModelMapper;

    /**
     * Finds the event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seat for show response dto list
     */
    public EventShowSeatForShowResponseDtoList findEventShowSeatsByEventShowId(@NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        final List<EventShowSeat> eventShowSeats = eventShowSeatService.findByEventShowId(eventShowId);

        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw EventShowSeatsNotFoundException.forEventShow(eventShowId);
        }
        return EventShowSeatForShowResponseDtoList.of(eventShowId, eventShowSeatForShowModelMapper.toDtos(eventShowSeats));
    }

    /**
     * Finds the event show seats by event show id.
     *
     * @param eventShowSeatPricesRequestDto the event show seat prices request dto
     * @return the event show seat prices response dto
     */
    public EventShowSeatPricesResponseDto findEventShowSeatsByEventShowId(
            @NotNull(
                message = "The event show id cannot be null."
            ) final EventShowSeatPricesRequestDto eventShowSeatPricesRequestDto) {

        final List<EventShowSeat> eventShowSeats = eventShowSeatService.findByEventShowAndSeatNumbers(
            eventShowSeatService.findEventShowById(eventShowSeatPricesRequestDto.getEventShowId()),
            eventShowSeatPricesRequestDto.getEventShowSeatNumbers());

        if (CollectionUtils.isEmpty(eventShowSeats)
            || eventShowSeats.size() != eventShowSeatPricesRequestDto.getEventShowSeatNumbers().size()) {
            throw new EventShowSeatsNotFoundException("Invalid event show id and seat numbers combination.");
        }

        return eventShowSeatPricesModelMapper.toEventShowSeatPricesResponseDto(eventShowSeatPricesRequestDto.getEventShowId(),
            eventShowSeats);
    }

    /**
     * Reserve.
     *
     * @param eventShowSeatsReservationRequestDto the event show seats reservation request dto
     * @return the event show seats reservation response dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatsReservationResponseDto reserve(@NotNull(message = "The reservation request cannot be null.") final EventShowSeatsReservationRequestDto eventShowSeatsReservationRequestDto) {

        validateRequiredDataForReservation(eventShowSeatsReservationRequestDto.getEventShowId(), eventShowSeatsReservationRequestDto.getSeatNumbers());

        final String bookingReservationId = UUID.randomUUID().toString();
        final Long reservedSeatsCount = eventShowSeatRepository.reserve(eventShowSeatsReservationRequestDto.getEventShowId(),
                                        eventShowSeatsReservationRequestDto.getSeatNumbers(),
                                        bookingReservationId,
                                        eventShowSeatsReservationRequestDto.getSeatNumbers().size());

        if (reservedSeatsCount == null || reservedSeatsCount != eventShowSeatsReservationRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException("The selected seats are no longer available. Please select different seats.");
        }

        return EventShowSeatsReservationResponseDto.of(true, bookingReservationId, eventShowSeatsReservationRequestDto);
    }

    /**
     * Book.
     *
     * @param eventShowSeatsBookingRequestDto the event show seats booking request dto
     * @return the event show seats booking response dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatsBookingResponseDto book(@NotNull(message = "The booking request cannot be null.") final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto) {

        validate(eventShowSeatsBookingRequestDto);

        final Long bookedSeatsCount = eventShowSeatRepository.book(eventShowSeatsBookingRequestDto.getEventShowId(), eventShowSeatsBookingRequestDto.getSeatNumbers(), eventShowSeatsBookingRequestDto.getBookingReservationId(), eventShowSeatsBookingRequestDto.getSeatNumbers().size());

        if (bookedSeatsCount == null || bookedSeatsCount != eventShowSeatsBookingRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException("The selected seats are no longer available. Please select different seats.");
        }

        return EventShowSeatsBookingResponseDto.of(true, eventShowSeatsBookingRequestDto);
    }

    /**
     * Cancel.
     *
     * @param eventShowSeatsCancellationRequestDto the event show seats cancellation request dto
     * @return the event show seats cancellation response dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatsCancellationResponseDto cancel(
            @NotNull(
                message = "The cancellation request cannot be null."
            ) final EventShowSeatsCancellationRequestDto eventShowSeatsCancellationRequestDto) {

        validate(eventShowSeatsCancellationRequestDto);

        final Long cancelledSeatsCount = eventShowSeatRepository.cancel(eventShowSeatsCancellationRequestDto.getEventShowId(),
            eventShowSeatsCancellationRequestDto.getSeatNumbers(),
            UUID.fromString(eventShowSeatsCancellationRequestDto.getBookingReservationId()));

        if (cancelledSeatsCount == null || cancelledSeatsCount != eventShowSeatsCancellationRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException("Unable to cancel the seat reservations for the given request.");
        }
        return EventShowSeatsCancellationResponseDto.of(true, eventShowSeatsCancellationRequestDto.getBookingReservationId());
    }

    /**
     * Validate.
     *
     * @param eventShowSeatsBookingRequestDto the event show seats booking request dto
     */
    private void validate(final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto) {

        validateRequiredData(eventShowSeatsBookingRequestDto.getEventShowId(), eventShowSeatsBookingRequestDto.getSeatNumbers(),
            eventShowSeatsBookingRequestDto.getBookingReservationId());
    }

    /**
     * Validate.
     *
     * @param eventShowSeatsCancellationRequestDto the event show seats cancellation request dto
     */
    private void validate(final EventShowSeatsCancellationRequestDto eventShowSeatsCancellationRequestDto) {

        validateRequiredData(eventShowSeatsCancellationRequestDto.getEventShowId(),
            eventShowSeatsCancellationRequestDto.getSeatNumbers(),
            eventShowSeatsCancellationRequestDto.getBookingReservationId());
    }

    /**
     * Validate required data.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     * @param bookingReservationId the booking reservation id
     */
    public void validateRequiredData(final Long eventShowId, final Set<String> seatNumbers, final String bookingReservationId) {

        validateRequiredDataForReservation(eventShowId, seatNumbers);

        if (StringUtils.isBlank(bookingReservationId)) {
            throw new EventShowSeatReservationServiceException("The booking reservation id cannot be blank.");
        }
    }

    /**
     * Validate required data for reservation.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     */
    private void validateRequiredDataForReservation(final Long eventShowId, final Set<String> seatNumbers) {

        if (eventShowId == null) {
            throw new EventShowSeatReservationServiceException("The event show id cannot be null.");
        }

        if (CollectionUtils.isEmpty(seatNumbers)) {
            throw new EventShowSeatReservationServiceException("The seat numbers cannot be empty.");
        }
    }
}
