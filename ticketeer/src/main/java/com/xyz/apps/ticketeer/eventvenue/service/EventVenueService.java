/*
* Id: EventVenueService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueUpdationDto;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenue;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenueRepository;
import com.xyz.apps.ticketeer.eventvenue.service.modelmapper.EventVenueCreationModelMapper;
import com.xyz.apps.ticketeer.eventvenue.service.modelmapper.EventVenueModelMapper;
import com.xyz.apps.ticketeer.general.service.GeneralService;

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

    /** The event venue model mapper. */
    @Autowired
    private EventVenueModelMapper eventVenueModelMapper;

    /** The event venue validation service. */
    @Autowired
    private EventVenueValidationService eventVenueValidationService;

    /** The event venue creation model mapper. */
    @Autowired
    private EventVenueCreationModelMapper eventVenueCreationModelMapper;

    /**
     * Adds the event venue.
     *
     * @param eventVenueCreationDto the event venue creation dto
     * @return the event venue dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventVenueDto add(@NotNull(message = "The event venue details cannot be null.") final EventVenueCreationDto eventVenueCreationDto) {

        eventVenueValidationService.validateCity(eventVenueCreationDto.getCityId());

        final EventVenue eventVenue = eventVenueRepository.save(eventVenueCreationModelMapper.toEntity(eventVenueCreationDto));

        if (eventVenue == null) {
            throw new EventVenueServiceException("Failed to add event venue: " + eventVenueCreationDto);
        }

        return eventVenueModelMapper.toDto(eventVenue);
    }

    /**
     * Updates the.
     *
     * @param eventVenueUpdationDto the event venue updation dto
     * @return the event venue dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventVenueDto update(@NotNull(message = "The event venue details cannot be null.") final EventVenueUpdationDto eventVenueUpdationDto) {

        if (eventVenueUpdationDto.getId() == null) {
            throw new EventVenueServiceException("The event venue id cannot be null.");
        }

        EventVenue eventVenue = findEventVenueById(eventVenueUpdationDto.getId());
        eventVenue.setName(eventVenueUpdationDto.getName());
        eventVenue = eventVenueRepository.save(eventVenue);

        if (eventVenue == null) {
            throw new EventVenueServiceException("Failed to updated event venue: " + eventVenueUpdationDto);
        }

        return eventVenueModelMapper.toDto(eventVenue);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(@NotNull(message = "The event venue id cannot be null.") final Long id) {

        if (!eventVenueRepository.existsById(id)) {
            throw new EventVenueNotFoundException(id);
        }

        eventVenueRepository.deleteById(id);
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the event venue dto
     */
    public EventVenueDto findById(@NotNull(message = "The event venue id cannot be null.") final Long id) {
        return eventVenueModelMapper.toDto(findEventVenueById(id));
    }

    /**
     * Finds the by city id.
     *
     * @param cityId the city id
     * @return the event venue dto
     */
    public EventVenueDtoList findByCityId(@NotNull(message = "The city id cannot be null.") final Long cityId) {
        final List<EventVenue> eventVenuesByCityId = eventVenueRepository.findByCityId(cityId);
        if (CollectionUtils.isEmpty(eventVenuesByCityId)) {
            throw EventVenueNotFoundException.forCityId(cityId);
        }
        return EventVenueDtoList.of(eventVenueModelMapper.toDtos(eventVenuesByCityId));
    }

    /**
     * Finds the event venue by id.
     *
     * @param id the id
     * @return the event venue
     */
    private EventVenue findEventVenueById(final Long id) {

        return eventVenueRepository.findById(id).orElseThrow(() -> new EventVenueNotFoundException(id));
    }
}
