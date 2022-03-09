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

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumRepository;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeat;
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
     * Finds the auditorium by id.
     *
     * @param auditoriumId the auditorium id
     * @return the auditorium dto
     */
    public AuditoriumDto findAuditoriumById(@NotNull(message = "The event venue id cannot be null.") final Long auditoriumId) {
        return auditoriumModelMapper.toDto(auditoriumRepository.findById(auditoriumId).orElseThrow(() -> new AuditoriumNotFoundException(auditoriumId)));
    }
}
