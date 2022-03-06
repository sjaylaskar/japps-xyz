/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.AuditoriumSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.EventVenueService;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.EventShowSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.EventShowSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.SeatReservationStatus;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.SeatRowPriceDto;
import com.xyz.apps.ticketeer.util.Environment;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;
import com.xyz.apps.ticketeer.util.WebClientBuilder;

import reactor.core.publisher.Mono;


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

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    /** The auditorium seat repository. */
    @Autowired
    private AuditoriumSeatRepository auditoriumSeatRepository;

    /** The event show model mapper. */
    @Autowired
    private EventShowModelMapper eventShowModelMapper;

    /**
     * Adds the event show.
     *
     * @param eventShowDetailsDto the event show details dto
     * @return the event show
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowDto add(@NotNull(message = "The event show details cannot be null.") final EventShowDetailsDto eventShowDetailsDto) {

        Objects.requireNonNull(eventShowDetailsDto, "The event show details cannot be null.");

        validate(eventShowDetailsDto);

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

        return eventShowModelMapper.toDto(eventShow);
    }

    /**
     * Validate.
     *
     * @param eventShowDetailsDto the event show details dto
     */
    private void validate(@NotNull(message = "The event show details cannot be null.") final EventShowDetailsDto eventShowDetailsDto) {

        validateCity(eventShowDetailsDto.getCityId());

        validateEventId(eventShowDetailsDto.getEventId());

        validateEventVenueId(eventShowDetailsDto.getEventVenueId());

        validateAuditoriumId(eventShowDetailsDto.getAuditoriumId());
    }

    /**
     * Validate city.
     *
     * @param cityId the city id
     */
    private void validateCity(@NotNull(message = "The city id cannot be null") final Long cityId) {

        WebClientBuilder.get().build()
        .get()
        .uri(Environment.property(ApiPropertyKey.GET_CITY_BY_ID.get(cityId)))
        .retrieve()
        .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                  response -> Mono.error(new EventShowServiceException(response.bodyToMono(String.class).block())));

    }

    /**
     * Validate event id.
     *
     * @param eventId the event id
     */
    private void validateEventId(final Long eventId) {
        WebClientBuilder.get().build()
        .get()
        .uri(Environment.property(ApiPropertyKey.GET_EVENT_BY_ID.get(eventId)))
        .retrieve()
        .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                  response -> Mono.error(new EventShowServiceException(response.bodyToMono(String.class).block())));
    }

    /**
     * Validate event venue id.
     *
     * @param eventVenueId the event venue id
     */
    private void validateEventVenueId(final Long eventVenueId) {
         eventVenueService.findById(eventVenueId);
    }

    /**
     * Validate auditorium id.
     *
     * @param auditoriumId the auditorium id
     */
    private void validateAuditoriumId(final Long auditoriumId) {
        eventVenueService.findAuditoriumById(auditoriumId);
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
     * Finds the event show by id.
     *
     * @param id the id
     * @return the event show dto
     */
    public EventShowDto findById(@NotNull(message = "The event show id cannot be null.") final Long id) {

        Objects.requireNonNull(id, "The event show id cannot be null.");

        return eventShowModelMapper.toDto(eventShowRepository.findById(id).orElseThrow(() -> new EventShowNotFoundException(id)));

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
                    null,
                    null))
                .toList()
            : List.of();
    }

    /**
     * To event show.
     *
     * @param eventShowDetailsDto the event show details dto
     * @return the event show
     */
    private EventShow toEventShow(final EventShowDetailsDto eventShowDetailsDto) {

        return eventShowModelMapper.toEntity(eventShowDetailsDto.toEventShowDto());
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the {@link EventShowDtoList}
     */
    public EventShowDtoList search(final EventShowSearchCriteria eventShowSearchCriteria) {
        return EventShowDtoList.of(eventShowModelMapper.toDtos(eventShowRepository.findByEventShowSearchCriteria(
            eventShowSearchCriteria.getCityId(),
            eventShowSearchCriteria.getEventId(),
            LocalDateTimeFormatUtil.parseLocalDate(eventShowSearchCriteria.getDate()))));
    }

    /**
     * Finds the by city id.
     *
     * @param cityId the city id
     * @return the event show dto list
     */
    public EventShowDtoList findByCityId(@NotNull(message = "The city id cannot be null.") final Long cityId) {

        return EventShowDtoList.of(eventShowModelMapper.toDtos(eventShowRepository.findByCityId(cityId)));
    }
}
