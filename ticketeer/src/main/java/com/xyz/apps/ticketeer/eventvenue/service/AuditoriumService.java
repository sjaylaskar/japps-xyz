/*
 * Id: AuditoriumService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumCreationDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumCreationDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumDtoList;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumCreationModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumRepository;
import com.xyz.apps.ticketeer.eventvenue.model.EventVenueModelMapper;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.CollectionUtil;


/**
 * The auditorium service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class AuditoriumService extends GeneralService {

    /** The auditorium repository. */
    @Autowired
    private AuditoriumRepository auditoriumRepository;

    /** The event venue model mapper. */
    @Autowired
    private EventVenueModelMapper eventVenueModelMapper;

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    /** The auditorium creation model mapper. */
    @Autowired
    private AuditoriumCreationModelMapper auditoriumCreationModelMapper;

    /** The auditorium model mapper. */
    @Autowired
    private AuditoriumModelMapper auditoriumModelMapper;

    /**
     * Adds the auditorium.
     *
     * @param auditoriumCreationDto the auditorium creation dto
     * @return the auditorium dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public AuditoriumDto add(@NotNull(
        message = "The auditorium cannot be null."
    ) final AuditoriumCreationDto auditoriumCreationDto) {

        validateAuditorium(auditoriumCreationDto);

        final Auditorium auditorium = auditoriumRepository.save(auditoriumCreationModelMapper.toEntity(auditoriumCreationDto));

        if (auditorium == null) {
            throw new AuditoriumServiceException("Failed to add auditorium: " + auditoriumCreationDto);
        }

        return auditoriumModelMapper.toDto(auditorium);
    }

    /**
     * Adds the all.
     *
     * @param auditoriumCreationDtoList the auditorium creation dto list
     * @return the auditorium dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public AuditoriumDtoList addAll(@NotNull(
        message = "The auditoriums list cannot be empty."
    ) final AuditoriumCreationDtoList auditoriumCreationDtoList) {

        validateAuditoriums(auditoriumCreationDtoList);

        final List<Auditorium> auditoriums = auditoriumRepository.saveAll(auditoriumCreationModelMapper.toAuditoriums(
            auditoriumCreationDtoList));

        if (CollectionUtils.isNotEmpty(auditoriums)) {
            throw new AuditoriumServiceException("Failed to add auditoriums.");
        }

        return auditoriumModelMapper.toAuditoriumDtoList(auditoriums);
    }

    /**
     * Updates the auditorium.
     *
     * @param auditoriumDto the auditorium dto
     * @return the auditorium dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public AuditoriumDto update(@NotNull(message = "The auditorium cannot be null.") final AuditoriumDto auditoriumDto) {

        final EventVenueDto eventVenueDto = validateAndFindEventVenue(auditoriumDto.getEventVenueId());

        final List<Auditorium> auditoriums = auditoriumRepository.findByEventVenueIdAndId(eventVenueDto.getId(), auditoriumDto
            .getId());
        if (CollectionUtils.isEmpty(auditoriums)) {
            throw AuditoriumNotFoundException.forEventVenueIdAndId(auditoriumDto.getEventVenueId(), auditoriumDto
                .getId());
        }

        if (!StringUtils.equals(auditoriumDto.getName(), auditoriums.get(0).getName())
            && findByEventVenueAndAuditoriumName(auditoriumDto.getEventVenueId(), auditoriumDto.getName()) != null) {
            throw new AuditoriumAlreadyExistsException("Auditorium with name: "
                + auditoriumDto.getName() + " already exists for event venue id: " + auditoriumDto.getEventVenueId());
        }

        auditoriums.get(0).setName(auditoriumDto.getName());
        return auditoriumModelMapper.toDto(auditoriumRepository.save(auditoriums.get(0)));
    }

    /**
     * Finds the by event venue and auditorium name.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @return the auditorium
     */
    public Auditorium findByEventVenueAndAuditoriumName(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName) {

        return auditoriumRepository.findByEventVenueAndName(eventVenueModelMapper.fromId(eventVenueId), auditoriumName);
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the auditorium
     */
    public Auditorium findById(@NotNull(message = "The auditorium id cannot be null.") final Long id) {
        return auditoriumRepository.findById(id).orElseThrow(() -> AuditoriumNotFoundException.forId(id));
    }

    /**
     * Delete.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByEventVenueIdAndAuditoriumName(
            @NotNull(message = "The event venue id cannot be null.") final Long eventVenueId,
            @NotBlank(message = "The auditorium name cannot be blank.") final String auditoriumName) {

        final Auditorium auditorium = findByEventVenueAndAuditoriumName(eventVenueId, auditoriumName);

        if (auditorium == null) {
            throw AuditoriumNotFoundException.forEventVenueIdAndName(eventVenueId, auditoriumName);
        }

        auditoriumRepository.deleteById(auditorium.getId());
    }

    /**
     * Finds the by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the auditorium dto list
     */
    public AuditoriumDtoList findByEventVenueId(@NotNull(message = "The event venue id cannot be null.") final Long eventVenueId) {

        final List<Auditorium> auditoriums = auditoriumRepository.findByEventVenue(eventVenueModelMapper.fromId(eventVenueId));

        if (CollectionUtils.isEmpty(auditoriums)) {
            throw AuditoriumNotFoundException.forEventVenueId(eventVenueId);
        }

        return auditoriumModelMapper.toAuditoriumDtoList(auditoriums);
    }

    /**
     * Validate auditorium.
     *
     * @param auditoriumCreationDto the auditorium creation dto
     */
    private void validateAuditorium(final AuditoriumCreationDto auditoriumCreationDto) {

        final EventVenueDto eventVenueDto = validateAndFindEventVenue(auditoriumCreationDto.getEventVenueId());

        if (StringUtils.isBlank(auditoriumCreationDto.getName())) {
            throw new AuditoriumServiceException("The auditorium name cannot be null.");
        }

        if (findByEventVenueAndAuditoriumName(auditoriumCreationDto.getEventVenueId(), auditoriumCreationDto.getName()) != null) {
            throw new AuditoriumAlreadyExistsException("Auditorium with name: "
                + auditoriumCreationDto.getName() + " already exists for event venue: " + eventVenueDto);
        }
    }

    /**
     * Validate auditoriums.
     *
     * @param auditoriumCreationDtoList the auditorium creation dto list
     */
    private void validateAuditoriums(final AuditoriumCreationDtoList auditoriumCreationDtoList) {

        if (CollectionUtils.isEmpty(auditoriumCreationDtoList.getAuditoriumNames())) {
            throw new AuditoriumServiceException("The auditoriums list cannot be empty.");
        }

        final EventVenueDto eventVenueDto = validateAndFindEventVenue(auditoriumCreationDtoList.getEventVenueId());

        if (auditoriumCreationDtoList.getAuditoriumNames().stream().filter(Objects::nonNull).toList().isEmpty() ||
            auditoriumCreationDtoList.getAuditoriumNames().stream().anyMatch(StringUtils::isBlank)) {
            throw new AuditoriumServiceException("The auditorium name cannot be blank.");
        }

        if (Set.of(auditoriumCreationDtoList.getAuditoriumNames()).size() != auditoriumCreationDtoList.getAuditoriumNames()
            .size()) {
            throw new AuditoriumServiceException("The auditorium names must be unique.");
        }

        final List<Auditorium> existingAuditoriumsForEventVenueAndNames = auditoriumRepository.findByEventVenueAndNames(
            eventVenueModelMapper.toEntity(eventVenueDto), auditoriumCreationDtoList.getAuditoriumNames());
        if (CollectionUtils.isNotEmpty(existingAuditoriumsForEventVenueAndNames)) {
            throw new AuditoriumAlreadyExistsException("Auditoriums with names: "
                + CollectionUtil.stringify(existingAuditoriumsForEventVenueAndNames.stream().map(Auditorium::getName)
                    .toList())
                + " already exist for event venue: " + eventVenueDto);
        }
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
