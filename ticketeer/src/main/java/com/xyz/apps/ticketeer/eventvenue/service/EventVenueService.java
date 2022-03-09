/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumRepository;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeat;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeatModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeatRepository;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenue;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenueModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenueRepository;
import com.xyz.apps.ticketeer.general.service.GeneralService;

import lombok.AllArgsConstructor;

/**
 * The event venue service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventVenueService extends GeneralService {

    /** The event venue repository. */
    @Autowired
    private EventVenueRepository eventVenueRepository;

    /** The auditorium repository. */
    @Autowired
    private AuditoriumRepository auditoriumRepository;

    /** The auditorium seat repository. */
    @Autowired
    private AuditoriumSeatRepository auditoriumSeatRepository;

    /** The event venue model mapper. */
    @Autowired
    private EventVenueModelMapper eventVenueModelMapper;

    /** The auditorium model mapper. */
    @Autowired
    private AuditoriumModelMapper auditoriumModelMapper;

    /** The event venue validation service. */
    @Autowired
    private EventVenueValidationService eventVenueValidationService;

    /** The auditorium seat model mapper. */
    @Autowired
    private AuditoriumSeatModelMapper auditoriumSeatModelMapper;

    /**
     * Adds the.
     *
     * @param eventVenueDetailsDto the event venue details dto
     * @return the event venue dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventVenueDto add(@NotNull(message = "The event venue details cannot be null.") final EventVenueDetailsDto eventVenueDetailsDto) {
        Objects.requireNonNull(eventVenueDetailsDto, "The event venue details cannot be null.");

        eventVenueValidationService.validateCity(eventVenueDetailsDto.getCityId());

        final EventVenue eventVenue = eventVenueRepository.save(toEventVenue(eventVenueDetailsDto));

        if (eventVenue != null) {
            final Map<String, AuditoriumSeatInfo> auditoriumInfoMap = toAuditoriumInfoMap(eventVenueDetailsDto.getAuditoriumDetailsDtoList().getAuditoriumDetailsDtos(), eventVenue);
            final List<Auditorium> auditoriumList = auditoriumRepository.saveAll(auditoriumInfoMap.values().stream().map(auditoriumSeatInfo -> auditoriumSeatInfo.auditorium).toList());

            if (CollectionUtils.isNotEmpty(auditoriumList)) {
                auditoriumSeatRepository.saveAll(
                auditoriumList
                .stream()
                .map(auditorium -> toAuditoriumSeatList(auditorium, auditoriumInfoMap.get(auditorium.getName())))
                .flatMap(List::stream)
                .toList());
            }
        }

        return eventVenueModelMapper.toDto(eventVenue);
    }

    /**
     * To auditorium seat list.
     *
     * @param auditorium the auditorium
     * @param auditoriumSeatInfo the auditorium seat info
     * @return the list
     */
    private List<AuditoriumSeat> toAuditoriumSeatList(final Auditorium auditorium, final AuditoriumSeatInfo auditoriumSeatInfo) {
        final List<AuditoriumSeat> auditoriumSeats = new ArrayList<>();

        char seatRow = 'A';
        for (int rowNumber = 1; rowNumber <= auditoriumSeatInfo.numberOfRows; rowNumber++) {
            for (int seat = 1; seat <= auditoriumSeatInfo.seatsPerRow; seat++) {
                auditoriumSeats.add(new AuditoriumSeat(seatRow, seat, auditorium));
            }
            seatRow++;
        }

        return auditoriumSeats;
    }

    /**
     * To event venue.
     *
     * @return the event venue
     */
    private EventVenue toEventVenue(final EventVenueDetailsDto eventVenueDetailsDto) {
        final EventVenue eventVenue = new EventVenue();
        eventVenue.setName(eventVenueDetailsDto.getName());
        eventVenue.setCityId(eventVenueDetailsDto.getCityId());
        eventVenue.setNumberOfAuditoriums(eventVenueDetailsDto.getNumberOfAuditoriums());
        return eventVenue;
    }

    /**
     * To auditorium info map.
     *
     * @param auditoriumDetailsList the auditorium details list
     * @param eventVenue the event venue
     * @return the map
     */
    private Map<String, AuditoriumSeatInfo> toAuditoriumInfoMap(
            final List<AuditoriumDetailsDto> auditoriumDetailsList,
            final EventVenue eventVenue) {
        if (CollectionUtils.isEmpty(auditoriumDetailsList)) {
            return Map.of();
        }

        return
        auditoriumDetailsList
        .stream()
        .collect(Collectors.toMap(AuditoriumDetailsDto::getName,
                                  auditoriumDetails -> new AuditoriumSeatInfo(new Auditorium(auditoriumDetails.getName(),
                                                                                             auditoriumDetails.getNumberOfRows() * auditoriumDetails.getSeatsPerRow(),
                                                                                             eventVenue),
                                                                              auditoriumDetails.getNumberOfRows(),
                                                                              auditoriumDetails.getSeatsPerRow())));
    }

    @AllArgsConstructor
    private static class AuditoriumSeatInfo {
        private Auditorium auditorium;

        private int numberOfRows;

        private int seatsPerRow;

    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the event venue dto
     */
    public EventVenueDto findById(@NotNull(message = "The event venue id cannot be null.") final Long id) {
        return eventVenueModelMapper.toDto(eventVenueRepository.findById(id).orElseThrow(() -> new EventVenueNotFoundException(id)));
    }

    /**
     * Finds the auditorium seat by id.
     *
     * @param auditoriumSeatId the auditorium seat id
     * @return the auditorium seat details dto
     */
    public AuditoriumSeatDetailsDto findAuditoriumSeatById(@NotNull(message = "The auditorium seat id cannot be null.") final Long auditoriumSeatId) {
          final AuditoriumSeat auditoriumSeat = auditoriumSeatRepository.findById(auditoriumSeatId)
                  .orElseThrow(() -> new EventVenueServiceException("Auditorium seat with id: " + auditoriumSeatId + " not found."));
          return auditoriumSeatModelMapper.toDto(auditoriumSeat);
    }

    /**
     * Finds the seat numbers by auditorium seat ids.
     *
     * @param auditoriumSeatIds the auditorium seat ids
     * @return the list
     */
    public List<String> findSeatNumbersByAuditoriumSeatIds(@NotEmpty(message = "The auditorium seat ids cannot be empty.") final List<Long> auditoriumSeatIds) {
        final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.findAllById(auditoriumSeatIds);
        if (CollectionUtils.isEmpty(auditoriumSeats) || auditoriumSeats.size() != auditoriumSeatIds.size()) {
            throw new EventVenueServiceException("Auditorium seats not found.");
        }

        return auditoriumSeats
                .stream()
                .map(auditoriumSeat -> String.valueOf(auditoriumSeat.getSeatRow()) + StringUtils.SPACE + auditoriumSeat.getSeatNumber()).toList();
    }

    /**
     * Finds the auditorium by id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium dto
     */
    public AuditoriumDto findAuditoriumById(@NotNull(message = "The auditorium id cannot be null.") final Long auditoriumId) {
        return auditoriumModelMapper.toDto(auditoriumRepository.findById(auditoriumId).orElseThrow(() -> new AuditoriumNotFoundException(auditoriumId)));
    }

    /**
     * Finds the auditorium seats by auditorium id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium seat dto list
     */
    public AuditoriumSeatDtoList findAuditoriumSeatsByAuditoriumId(@NotNull(message = "The auditorium id cannot be null.") final Long auditoriumId) {
        final AuditoriumDto auditoriumDto = findAuditoriumById(auditoriumId);
        final List<AuditoriumSeat> auditoriumSeats = auditoriumSeatRepository.findByAuditoriumId(auditoriumDto.getId());

        if (CollectionUtils.isEmpty(auditoriumSeats)) {
            throw new EventVenueServiceException("No auditorium seats found for auditorium id: " + auditoriumId);
        }
        return AuditoriumSeatDtoList.of(auditoriumDto.getId(), auditoriumDto.getName(),
            auditoriumSeats.stream().map(auditoriumSeat -> AuditoriumSeatDto.of(auditoriumSeat.getId(), auditoriumSeat.getSeatRow(), auditoriumSeat.getSeatNumber()))
            .toList());
    }
}
