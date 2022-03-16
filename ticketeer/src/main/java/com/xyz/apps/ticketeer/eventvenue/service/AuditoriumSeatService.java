/*
 * Id: AuditoriumSeatService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatRowCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatsCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.service.modelmapper.AuditoriumSeatModelMapper;
import com.xyz.apps.ticketeer.general.service.GeneralService;


/**
 * The auditorium seat service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class AuditoriumSeatService extends GeneralService {

    /** The auditorium seat repository. */
    @Autowired
    private AuditoriumSeatRepository auditoriumSeatRepository;

    /** The auditorium seat model mapper. */
    @Autowired
    private AuditoriumSeatModelMapper auditoriumSeatModelMapper;

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    /** The auditorium repository. */
    @Autowired
    private AuditoriumService auditoriumService;

    /**
     * Adds the auditorium seats.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     * @return the auditorium dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public AuditoriumSeatsDto add(
            @NotNull(message = "The auditorium seats cannot be null.") final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        return addOrUpdate(auditoriumSeatsCreationDto);

    }

    /**
     * Updates the auditorium.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     * @return the auditorium dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public AuditoriumSeatsDto update(
            @NotNull(message = "The auditorium seats cannot be null.") final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        return addOrUpdate(auditoriumSeatsCreationDto);
    }

    /**
     * Adds or updates the auditorium seats.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     * @return the auditorium seats dto
     */
    private AuditoriumSeatsDto addOrUpdate(final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        validateAuditoriumSeats(auditoriumSeatsCreationDto);

        final Auditorium auditorium = findAuditorium(auditoriumSeatsCreationDto);

        final List<AuditoriumSeat> auditoriumSeatsSaved = new ArrayList<>();
        for (final AuditoriumSeatRowCreationDto auditoriumSeatRowCreationDto : auditoriumSeatsCreationDto.getAuditoriumSeatRows()) {
            final Optional<AuditoriumSeat> auditoriumSeatOptional = auditoriumSeatRepository
                .findTopByAuditoriumAndRowNameOrderBySeatNumberDesc(auditorium, auditoriumSeatRowCreationDto.getRowName());
            final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.saveAll(
                toAuditoriumSeats(auditorium, auditoriumSeatRowCreationDto,
                    (auditoriumSeatOptional.isPresent())
                        ? (auditoriumSeatOptional.get().getSeatNumber() + 1)
                        : 1));
            if (CollectionUtils.isEmpty(auditoriumSeats)) {
                throw new AuditoriumSeatServiceException("Failed to add auditorium seats.");
            }
            auditoriumSeatsSaved.addAll(auditoriumSeats);
        }

        return auditoriumSeatModelMapper.toAuditoriumSeatsDto(auditoriumSeatsCreationDto.getEventVenueId(), auditorium.getName(),
            auditoriumSeatsSaved);
    }

    /**
     * Delete.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the long
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Long deleteAllSeatsForAuditorium(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName) {

        final Auditorium auditorium = validateAndFindAuditorium(eventVenueId, auditoriumName);

        final Long deletedSeatsCount = auditoriumSeatRepository.deleteByAuditorium(auditorium);

        if (deletedSeatsCount == null || deletedSeatsCount < 1) {
            throw AuditoriumSeatNotFoundException.forEventVenueAndAuditorium(eventVenueId, auditorium.getName());
        }

        return deletedSeatsCount;
    }

    /**
     * Delete.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @return the long
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Long deleteSeatRow(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName,
            @NotBlank(message = "The auditorium seat row name cannot be blank.") final String seatRowName) {

        final Auditorium auditorium = validateAndFindAuditorium(eventVenueId, auditoriumName);

        final Long deletedSeatsCount = auditoriumSeatRepository.deleteByAuditoriumAndRowName(auditorium, seatRowName);

        if (deletedSeatsCount == null || deletedSeatsCount < 1) {
            throw AuditoriumSeatNotFoundException.forEventVenueAndAuditoriumAndRowName(eventVenueId, auditorium.getName(), seatRowName);
        }

        return deletedSeatsCount;
    }

    /**
     * Delete seat.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param seatRowName the seat row name
     * @param seatNumber the seat number
     * @return the long
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Long deleteSeat(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName,
            @NotBlank(message = "The auditorium seat row name cannot be blank.") final String seatRowName,
            @NotBlank(message = "The auditorium seat row number cannot be blank.") final Integer seatNumber) {
        final Auditorium auditorium = validateAndFindAuditorium(eventVenueId, auditoriumName);

        final Long deletedSeatsCount = auditoriumSeatRepository.deleteByAuditoriumAndRowNameAndSeatNumber(auditorium, seatRowName, seatNumber);

        if (deletedSeatsCount == null || deletedSeatsCount < 1) {
            throw AuditoriumSeatNotFoundException.forSeatNumber(eventVenueId, auditorium.getName(), seatRowName, seatNumber);
        }

        return deletedSeatsCount;
    }

    /**
     * Finds the by event venue and auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the auditorium seats dto
     */
    public AuditoriumSeatsDto findByEventVenueAndAuditorium(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName) {

        final Auditorium auditorium = validateAndFindAuditorium(eventVenueId, auditoriumName);

        final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.findByAuditorium(auditorium);

        if (CollectionUtils.isEmpty(auditoriumSeats)) {
            throw AuditoriumSeatNotFoundException.forEventVenueAndAuditorium(eventVenueId, auditorium.getName());
        }

        return auditoriumSeatModelMapper.toAuditoriumSeatsDto(eventVenueId, auditoriumName, auditoriumSeats);

    }

    /**
     * Finds the by id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium
     */
    public AuditoriumSeatsDto findByAuditoriumId(@NotNull(message = "The auditorium id cannot be null.") final Long auditoriumId) {
        final Auditorium auditorium = auditoriumService.findById(auditoriumId);

        final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.findByAuditorium(auditorium);

        if (CollectionUtils.isEmpty(auditoriumSeats)) {
            throw AuditoriumSeatNotFoundException.forAuditoriumId(auditorium.getId());
        }

        return auditoriumSeatModelMapper.toAuditoriumSeatsDto(auditorium.getEventVenue().getId(), auditorium.getName(), auditoriumSeats);

    }

    /**
     * To auditorium seats.
     *
     * @param auditorium the auditorium
     * @param auditoriumSeatRowCreationDto the auditorium seat row creation dto
     * @param startSeatNumber the start seat number
     * @return the list
     */
    private List<AuditoriumSeat> toAuditoriumSeats(final Auditorium auditorium,
            final AuditoriumSeatRowCreationDto auditoriumSeatRowCreationDto, final Integer startSeatNumber) {

        final List<AuditoriumSeat> auditoriumSeats = new ArrayList<>();

        IntStream.range(startSeatNumber, startSeatNumber + auditoriumSeatRowCreationDto.getNumberOfSeats())
            .forEachOrdered(seatNumber -> auditoriumSeats.add(auditoriumSeatModelMapper.toAuditoriumSeat(auditorium,
                auditoriumSeatRowCreationDto.getRowName(), seatNumber)));

        return auditoriumSeats;
    }

    /**
     * Validate auditorium.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     */
    private void validateAuditoriumSeats(final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        if (CollectionUtils.isEmpty(auditoriumSeatsCreationDto.getAuditoriumSeatRows())) {
            throw new AuditoriumSeatServiceException("The auditorium seat rows cannot be empty.");
        }

        if (auditoriumSeatsCreationDto.getAuditoriumSeatRows()
            .stream()
            .map(AuditoriumSeatRowCreationDto::getRowName)
            .filter(Objects::nonNull)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toSet()).size() != auditoriumSeatsCreationDto.getAuditoriumSeatRows().size()) {
            throw new AuditoriumSeatServiceException("The auditorium seat row names must be unique and not blank.");
        }

        if (auditoriumSeatsCreationDto.getAuditoriumSeatRows().stream().map(AuditoriumSeatRowCreationDto::getNumberOfSeats)
            .anyMatch(numberOfSeats -> numberOfSeats < 1)) {
            throw new AuditoriumSeatServiceException("The number of seats in any row must be at least 1.");
        }
    }

    /**
     * Finds the auditorium.
     *
     * @param auditoriumSeatsCreationDto the auditorium seats creation dto
     * @return the auditorium
     */
    private Auditorium findAuditorium(final AuditoriumSeatsCreationDto auditoriumSeatsCreationDto) {

        return validateAndFindAuditorium(auditoriumSeatsCreationDto.getEventVenueId(), auditoriumSeatsCreationDto.getAuditoriumName());
    }

    /**
     * Validate and find auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the auditorium
     */
    private Auditorium validateAndFindAuditorium(final Long eventVenueId, final String auditoriumName) {

        final EventVenueDto eventVenueDto = validateAndFindEventVenue(eventVenueId);

        if (StringUtils.isBlank(auditoriumName)) {
            throw new AuditoriumSeatServiceException("The auditorium name cannot be null.");
        }

        return findAuditorium(eventVenueDto.getId(), auditoriumName);
    }

    /**
     * Finds the auditorium.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the auditorium
     */
    private Auditorium findAuditorium(final Long eventVenueId, final String auditoriumName) {

        final Auditorium auditorium = auditoriumService.findByEventVenueAndAuditoriumName(eventVenueId, auditoriumName);
        if (auditorium == null) {
            throw new AuditoriumSeatServiceException("No auditorium with name: "
                + auditoriumName + " for event venue: " +  eventVenueId);
        }
        return auditorium;
    }

    /**
     * Validate and find event venue.
     *
     * @param eventVenueId the event venue id
     * @return the event venue dto
     */
    private EventVenueDto validateAndFindEventVenue(final Long eventVenueId) {

        final EventVenueDto eventVenueDto = eventVenueService.findById(eventVenueId);

        if (eventVenueDto == null) {
            throw new AuditoriumServiceException("Event venue not found for id: " + eventVenueId);
        }

        return eventVenueDto;
    }

}
