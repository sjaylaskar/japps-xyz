/*
 * Id: EventShowSeatService.java 06-Mar-2022 4:58:33 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatRowDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatsDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShowModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatInformationResponseDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatModificationResponseDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatNumberPriceDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatPricesUpdationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatRowPriceDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatsCreationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeat;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model.EventShowSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatInfoModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.service.modelmapper.EventShowSeatModificationModelMapper;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.EventShowService;
import com.xyz.apps.ticketeer.eventvenue.service.AuditoriumSeatService;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.CollectionUtil;


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
    private EventShowSeatModificationModelMapper eventShowSeatModelMapper;

    /** The event show seat info model mapper. */
    @Autowired
    private EventShowSeatInfoModelMapper eventShowSeatInfoModelMapper;

    /** The event show service. */
    @Autowired
    private EventShowService eventShowService;

    /** The event show model mapper. */
    @Autowired
    private EventShowModelMapper eventShowModelMapper;

    /** The auditorium seat service. */
    @Autowired
    private AuditoriumSeatService auditoriumSeatService;

    /**
     * Adds the.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @return the auditorium seats dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatModificationResponseDtoList add(@NotNull(
        message = "The event show seats cannot be null."
    ) final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        return addOrUpdate(eventShowSeatsCreationDto);

    }

    /**
     * Updates the.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @return the event show seat creation response dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatModificationResponseDtoList update(@NotNull(
        message = "The event show seats cannot be null."
    ) final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        return addOrUpdate(eventShowSeatsCreationDto);
    }

    /**
     * Updates the.
     *
     * @param eventShowSeatPriceUpdationDto the event show seat price updation dto
     * @return the event show seat creation response dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowSeatModificationResponseDtoList update(
            @NotNull(
                message = "The event show seats cannot be null."
            ) final EventShowSeatPricesUpdationDto eventShowSeatPriceUpdationDto) {

        validateEventShowSeatNumberPrices(eventShowSeatPriceUpdationDto);

        final EventShow eventShow = findEventShowById(eventShowSeatPriceUpdationDto.getEventShowId());

        final List<EventShowSeat> eventShowSeatsUpdated = updateEventShowSeatNumberPrices(eventShowSeatPriceUpdationDto, eventShow);

        return EventShowSeatModificationResponseDtoList.of(eventShowSeatModelMapper.toDtos(eventShowSeatsUpdated));
    }

    /**
     * Delete by event show and seat row.
     *
     * @param eventShowId the event show id
     * @param rowName the row name
     * @return the long
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Long deleteByEventShowAndSeatRow(
            @NotNull(message = "The event show id cannot be null.") final Long eventShowId,
            @NotBlank(message = "The seat row name cannot be blank.") final String rowName) {

        final Long countOfDeletedRecords = eventShowSeatRepository.deleteByEventShowAndRowName(eventShowModelMapper.fromId(
            eventShowId), rowName);

        if (countOfDeletedRecords == null || countOfDeletedRecords < 1) {
            throw EventShowSeatsNotFoundException.forEventShowAndRowName(eventShowId, rowName);
        }
        return countOfDeletedRecords;
    }

    /**
     * Delete by event show and seat number.
     *
     * @param eventShowId the event show id
     * @param seatNumber the seat number
     * @return the long
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Long deleteByEventShowAndSeatNumber(
            @NotNull(message = "The event show id cannot be null.") final Long eventShowId,
            @NotBlank(message = "The seat number cannot be blank.") final String seatNumber) {

        final Long countOfDeletedRecords = eventShowSeatRepository.deleteByEventShowAndSeatNumber(eventShowModelMapper.fromId(
            eventShowId), seatNumber);

        if (countOfDeletedRecords == null || countOfDeletedRecords < 1) {
            throw EventShowSeatsNotFoundException.forEventShowAndSeatNumber(eventShowId, seatNumber);
        }
        return countOfDeletedRecords;
    }

    /**
     * Finds the event show seats by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seat dto list
     */
    public EventShowSeatInformationResponseDtoList findEventShowSeatsByEventShowId(@NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        final List<EventShowSeat> eventShowSeats = findByEventShowId(eventShowId);

        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw EventShowSeatsNotFoundException.forEventShow(eventShowId);
        }

        return EventShowSeatInformationResponseDtoList.of(eventShowId, eventShowSeatInfoModelMapper.toDtos(eventShowSeats));
    }

    /**
     * Finds the by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seats
     */
    protected List<EventShowSeat> findByEventShowId(@NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        return eventShowSeatRepository
            .findByEventShow(
                eventShowModelMapper.fromId(eventShowId));
    }

    /**
     * @param eventShowSeatPriceUpdationDto
     */
    private void validateEventShowSeatNumberPrices(final EventShowSeatPricesUpdationDto eventShowSeatPriceUpdationDto) {

        if (CollectionUtils.isEmpty(eventShowSeatPriceUpdationDto.getEventShowSeatNumberPrices())) {
            throw new EventShowSeatServiceException("The event show seat number prices cannot be empty.");
        }

        if (eventShowSeatPriceUpdationDto
            .getEventShowSeatNumberPrices().stream()
            .filter(Objects::nonNull)
            .map(EventShowSeatNumberPriceDto::getSeatNumber)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toSet()).size() != eventShowSeatPriceUpdationDto.getEventShowSeatNumberPrices().size()) {
            throw new EventShowSeatServiceException(
                "The event show seat number prices cannot be null and must be for unique seat numbers.");
        }
    }

    /**
     * Updates the event show seat number prices.
     *
     * @param eventShowSeatPriceUpdationDto the event show seat price updation dto
     * @param eventShow the event show
     * @return the list
     */
    private List<EventShowSeat> updateEventShowSeatNumberPrices(final EventShowSeatPricesUpdationDto eventShowSeatPriceUpdationDto,
            final EventShow eventShow) {

        final Map<String, List<EventShowSeat>> eventShowSeatsBySeatNumber = findByEventShowAndSeatNumbers(eventShow,
            eventShowSeatPriceUpdationDto
                .getEventShowSeatNumberPrices().stream().map(EventShowSeatNumberPriceDto::getSeatNumber).collect(Collectors
                    .toSet()))
                        .stream().collect(Collectors.groupingBy(EventShowSeat::getSeatNumber));

        if (MapUtils.isEmpty(eventShowSeatsBySeatNumber)) {
            throw new EventShowSeatsNotFoundException("No event show seats found to update.");
        }

        eventShowSeatPriceUpdationDto.getEventShowSeatNumberPrices()
            .stream().forEach(eventShowSeatNumberPrice -> eventShowSeatsBySeatNumber.get(eventShowSeatNumberPrice.getSeatNumber())
                .get(0).setAmount(eventShowSeatNumberPrice.getAmount()));

        final List<EventShowSeat> eventShowSeatsUpdated = eventShowSeatRepository.saveAll(eventShowSeatsBySeatNumber.values()
            .stream()
            .flatMap(List::stream).toList());

        if (CollectionUtils.isEmpty(eventShowSeatsUpdated)) {
            throw new EventShowSeatServiceException("Failed to update event show seat prices.");
        }
        return eventShowSeatsUpdated;
    }

    /**
     * Finds the by event show and seat numbers.
     *
     * @param eventShow the event show
     * @param seatNumbers the seat numbers
     * @return the list
     */
    protected List<EventShowSeat> findByEventShowAndSeatNumbers(
            @NotNull(message = "The event show cannot be null.") final EventShow eventShow,
            @NotEmpty(message = "The seat numbers cannot be empty.") final Set<String> seatNumbers) {

        return eventShowSeatRepository.findByEventShowAndSeatNumbers(eventShow, seatNumbers);
    }

    /**
     * Adds the or update.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @return the event show seat creation response dto list
     */
    private EventShowSeatModificationResponseDtoList addOrUpdate(final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        final EventShow eventShow = findEventShowById(eventShowSeatsCreationDto.getEventShowId());

        validateEventShowSeatPrices(eventShowSeatsCreationDto);

        final AuditoriumSeatsDto auditoriumSeatsDto = auditoriumSeatService.findByAuditoriumId(eventShow.getAuditorium().getId());

        final Map<String, List<AuditoriumSeatRowDto>> auditoriumSeatsByRowMap = auditoriumSeatsDto.getAuditoriumSeatRows().stream()
            .collect(Collectors.groupingBy(AuditoriumSeatRowDto::getRowName));

        validateEventShowSeatRows(eventShowSeatsCreationDto, auditoriumSeatsByRowMap);

        return addOrUpdate(eventShowSeatsCreationDto, eventShow, auditoriumSeatsByRowMap);
    }

    /**
     * Finds the event show by id.
     *
     * @param eventShowId the event show id
     * @return the event show
     */
    protected EventShow findEventShowById(@NotNull(message = "The event show id cannot be null.") final Long eventShowId) {

        return eventShowService.findEventShowById(eventShowId);
    }

    /**
     * Adds the or update.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @param eventShow the event show
     * @param auditoriumSeatsByRowMap the auditorium seats by row map
     * @return the event show seat creation response dto list
     */
    private EventShowSeatModificationResponseDtoList addOrUpdate(final EventShowSeatsCreationDto eventShowSeatsCreationDto,
            final EventShow eventShow, final Map<String, List<AuditoriumSeatRowDto>> auditoriumSeatsByRowMap) {

        final List<EventShowSeat> eventShowSeatsSaved = new ArrayList<>();
        eventShowSeatsCreationDto.getEventShowSeatRowPrices()
            .stream()
            .forEach(eventShowSeatRowPrice -> {
                final List<EventShowSeat> existingEventShowSeatsForRow = eventShowSeatRepository.findByEventShowAndRowName(
                    eventShow, eventShowSeatRowPrice.getAuditoriumSeatRowName());
                if (CollectionUtils.isNotEmpty(existingEventShowSeatsForRow)) {
                    updateEventShowSeats(eventShowSeatRowPrice, existingEventShowSeatsForRow, eventShowSeatsSaved);
                } else {
                    addEventShowSeats(eventShowSeatRowPrice, eventShow, auditoriumSeatsByRowMap, eventShowSeatsSaved);
                }
            });

        return EventShowSeatModificationResponseDtoList.of(eventShowSeatModelMapper.toDtos(eventShowSeatsSaved));
    }

    /**
     * Adds the event show seats.
     *
     * @param eventShowSeatRowPrice the event show seat row price
     * @param eventShow the event show
     * @param auditoriumSeatsByRowMap the auditorium seats by row map
     * @param eventShowSeatsSaved the event show seats saved
     */
    private void addEventShowSeats(final EventShowSeatRowPriceDto eventShowSeatRowPrice, final EventShow eventShow,
            final Map<String, List<AuditoriumSeatRowDto>> auditoriumSeatsByRowMap, final List<EventShowSeat> eventShowSeatsSaved) {

        final List<EventShowSeat> eventShowSeats = eventShowSeatRepository.saveAll(
            auditoriumSeatsByRowMap.get(eventShowSeatRowPrice.getAuditoriumSeatRowName())
                .stream()
                .map(AuditoriumSeatRowDto::getSeats)
                .flatMap(List::stream)
                .map(auditoriumSeat -> eventShowSeatModelMapper.toEventShowSeat(eventShow, eventShowSeatRowPrice,
                    auditoriumSeat))
                .toList());
        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw new EventShowSeatServiceException("Failed to save event show seats info.");
        }
        eventShowSeatsSaved.addAll(eventShowSeats);
    }

    /**
     * Updates the event show seats.
     *
     * @param eventShowSeatRowPrice the event show seat row price
     * @param existingEventShowSeatsForRow the existing event show seats for row
     * @param eventShowSeatsSaved the event show seats saved
     */
    private void updateEventShowSeats(final EventShowSeatRowPriceDto eventShowSeatRowPrice,
            final List<EventShowSeat> existingEventShowSeatsForRow, final List<EventShowSeat> eventShowSeatsSaved) {

        existingEventShowSeatsForRow.forEach(existingEventShowSeatForRow -> {
            existingEventShowSeatForRow.setAmount(eventShowSeatRowPrice.getAmount());
        });
        final List<EventShowSeat> eventShowSeats = eventShowSeatRepository.saveAll(existingEventShowSeatsForRow);
        if (CollectionUtils.isEmpty(eventShowSeats)) {
            throw new EventShowSeatServiceException("Failed to save event show seats info.");
        }
        eventShowSeatsSaved.addAll(eventShowSeats);
    }

    /**
     * Validate event show seat rows.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     * @param auditoriumSeatsByRowMap the auditorium seats by row map
     */
    private void validateEventShowSeatRows(final EventShowSeatsCreationDto eventShowSeatsCreationDto,
            final Map<String, List<AuditoriumSeatRowDto>> auditoriumSeatsByRowMap) {

        final Set<String> invalidAuditoriumSeatRowNames = eventShowSeatsCreationDto.getEventShowSeatRowPrices().stream()
            .map(EventShowSeatRowPriceDto::getAuditoriumSeatRowName)
            .filter(auditoriumSeatRowName -> !auditoriumSeatsByRowMap.containsKey(auditoriumSeatRowName))
            .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(invalidAuditoriumSeatRowNames)) {
            throw new EventShowSeatServiceException("Invalid auditorium seat row names provided: "
                + CollectionUtil.stringify(invalidAuditoriumSeatRowNames));
        }
    }

    /**
     * Validate event show seat prices.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     */
    private void validateEventShowSeatPrices(final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        if (CollectionUtils.isEmpty(eventShowSeatsCreationDto.getEventShowSeatRowPrices())) {
            throw new EventShowSeatServiceException("The event show seat row prices cannot be empty.");
        }

        validateEventShowSeatUniqueRowNames(eventShowSeatsCreationDto);

        validateEventShowSeatAmounts(eventShowSeatsCreationDto);
    }

    /**
     * Validate event show seat amounts.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     */
    private void validateEventShowSeatAmounts(final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        if (eventShowSeatsCreationDto.getEventShowSeatRowPrices().stream()
            .anyMatch(eventShowSeatRowPrice -> eventShowSeatRowPrice.getAmount() == null
                || eventShowSeatRowPrice.getAmount() < 0)) {
            throw new EventShowSeatServiceException("The seat row prices cannot be negative.");
        }
    }

    /**
     * Validate event show seat unique row names.
     *
     * @param eventShowSeatsCreationDto the event show seats creation dto
     */
    private void validateEventShowSeatUniqueRowNames(final EventShowSeatsCreationDto eventShowSeatsCreationDto) {

        if (eventShowSeatsCreationDto
            .getEventShowSeatRowPrices().stream()
            .filter(Objects::nonNull)
            .map(EventShowSeatRowPriceDto::getAuditoriumSeatRowName)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toSet()).size() != eventShowSeatsCreationDto.getEventShowSeatRowPrices().size()) {
            throw new EventShowSeatServiceException("The event show seat prices cannot be null and must be for unique row names.");
        }
    }
}
