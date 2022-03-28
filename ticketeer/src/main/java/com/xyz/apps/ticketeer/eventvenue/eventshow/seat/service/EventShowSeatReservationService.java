/*
 * Id: EventShowSeatReservationService.java 06-Mar-2022 4:58:33 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.eventshow.resources.Messages;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatForShowResponseDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatInformationResponseDtoList;
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
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatInfoModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatPricesModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.modelmapper.EventShowModelMapper;
import com.xyz.apps.ticketeer.general.resources.EnvironmentProperties;
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

    /** The event show model mapper. */
    @Autowired
    private EventShowModelMapper eventShowModelMapper;

    /** The event show seat info model mapper. */
    @Autowired
    private EventShowSeatInfoModelMapper eventShowSeatInfoModelMapper;

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
     * Finds the seat prices.
     *
     * @param eventShowSeatPricesRequestDto the event show seat prices request dto
     * @return the event show seat prices response dto
     */
    public EventShowSeatPricesResponseDto findSeatPrices(
            @NotNull(
                message = "The event show seat prices request cannot be null."
            ) final EventShowSeatPricesRequestDto eventShowSeatPricesRequestDto) {

        final List<EventShowSeat> eventShowSeats = eventShowSeatService.findByEventShowAndSeatNumbers(
            eventShowSeatService.findEventShowById(eventShowSeatPricesRequestDto.getEventShowId()),
            eventShowSeatPricesRequestDto.getEventShowSeatNumbers());

        if (CollectionUtils.isEmpty(eventShowSeats)
            || eventShowSeats.size() != eventShowSeatPricesRequestDto.getEventShowSeatNumbers().size()) {
            throw new EventShowSeatsNotFoundException(Messages.MESSAGE_ERROR_INVALID_COMBINATION_OF_EVENT_SHOW_AND_SEATS);
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
        final Integer reservedSeatsCount = eventShowSeatRepository.reserve(eventShowSeatsReservationRequestDto.getEventShowId(),
                                        eventShowSeatsReservationRequestDto.getSeatNumbers(),
                                        bookingReservationId,
                                        eventShowSeatsReservationRequestDto.getSeatNumbers().size());

        if (reservedSeatsCount == null || reservedSeatsCount != eventShowSeatsReservationRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_SELECTED_SEATS_NO_LONGER_AVAILABLE);
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

        final Integer bookedSeatsCount = eventShowSeatRepository.book(eventShowSeatsBookingRequestDto.getEventShowId(), eventShowSeatsBookingRequestDto.getSeatNumbers(), eventShowSeatsBookingRequestDto.getBookingReservationId(), eventShowSeatsBookingRequestDto.getSeatNumbers().size());

        if (bookedSeatsCount == null || bookedSeatsCount != eventShowSeatsBookingRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_SELECTED_SEATS_NO_LONGER_AVAILABLE);
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

        final Integer cancelledSeatsCount = eventShowSeatRepository.cancel(eventShowModelMapper.fromId(eventShowSeatsCancellationRequestDto.getEventShowId()),
            eventShowSeatsCancellationRequestDto.getSeatNumbers(),
            UUID.fromString(eventShowSeatsCancellationRequestDto.getBookingReservationId()));

        if (cancelledSeatsCount == null || cancelledSeatsCount != eventShowSeatsCancellationRequestDto.getSeatNumbers().size()) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_FAILURE_CANCEL);
        }
        return EventShowSeatsCancellationResponseDto.of(true, eventShowSeatsCancellationRequestDto.getBookingReservationId());
    }

    /**
     * Finds the by event show id and booking reservation id.
     *
     * @param eventShowId the event show id
     * @param bookingReservationId the booking reservation id
     * @return the event show seat information response dto list
     */
    public EventShowSeatInformationResponseDtoList findByEventShowIdAndBookingReservationId(
            @NotNull(message = "The event show id cannot be null.") final Long eventShowId,
            @NotBlank(message = "The booking reservation id cannot be blank.") final String bookingReservationId) {

        final List<EventShowSeat> eventShowSeatsByReservation = eventShowSeatRepository.findByEventShowAndBookingReservationId(eventShowModelMapper.fromId(eventShowId), UUID.fromString(bookingReservationId));

        if (CollectionUtils.isEmpty(eventShowSeatsByReservation)) {
            throw new EventShowSeatsNotFoundException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_EVENT_SHOW_ID_AND_RESERVATION_ID, eventShowId, bookingReservationId);
        }

        return EventShowSeatInformationResponseDtoList.of(eventShowId, eventShowSeatInfoModelMapper.toDtos(eventShowSeatsByReservation));
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
    private void validateRequiredData(final Long eventShowId, final Set<String> seatNumbers, final String bookingReservationId) {

        validateRequiredDataForReservation(eventShowId, seatNumbers);

        if (StringUtils.isBlank(bookingReservationId)) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_NOT_NULL_BOOKING_RESERVATION_ID);
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
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_NOT_NULL_EVENT_SHOW_ID);
        }

        if (CollectionUtils.isEmpty(seatNumbers)) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_NOT_EMPTY_SEAT_NUMBERS);
        }

        final int maxSeatsPerBooking = EnvironmentProperties.get(environment()).maxSeatsPerBooking();
        if (seatNumbers.size() > maxSeatsPerBooking) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_MAX_SEATS_PER_BOOKING, maxSeatsPerBooking);
        }

        validateSeatsExistForShow(eventShowId, seatNumbers);
    }

    /**
     * Validate seats exist for show.
     *
     * @param eventShowId the event show id
     * @param seatNumbers the seat numbers
     */
    private void validateSeatsExistForShow(final Long eventShowId, final Set<String> seatNumbers) {

        if (CollectionUtils.isEmpty(eventShowSeatService.findByEventShowAndSeatNumbers(eventShowModelMapper.fromId(eventShowId), seatNumbers))) {
            throw new EventShowSeatReservationServiceException(Messages.MESSAGE_ERROR_NOT_FOUND_FOR_EVENT_SHOW_ID_AND_SEAT_NUMBERS, eventShowId, seatNumbers);
        }
    }
}
