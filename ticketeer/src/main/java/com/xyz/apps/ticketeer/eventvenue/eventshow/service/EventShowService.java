/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.eventvenue.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.EventDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.external.contract.EventDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.EventVenueDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowCreationDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowDetailsDtoList;
import com.xyz.apps.ticketeer.eventvenue.eventshow.api.internal.contract.EventShowDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShowRepository;
import com.xyz.apps.ticketeer.eventvenue.eventshow.service.modelmapper.EventShowModelMapper;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.service.AuditoriumService;
import com.xyz.apps.ticketeer.eventvenue.service.EventVenueExternalApiHandlerService;
import com.xyz.apps.ticketeer.eventvenue.service.EventVenueService;
import com.xyz.apps.ticketeer.eventvenue.service.EventVenueValidationService;
import com.xyz.apps.ticketeer.general.resources.EnvironmentProperties;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.util.LocalDateTimeFormatUtil;


/**
 * The event show service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
public class EventShowService extends GeneralService {

    /** The event show repository. */
    @Autowired
    private EventShowRepository eventShowRepository;

    /** The event venue service. */
    @Autowired
    private EventVenueService eventVenueService;

    /** The event show model mapper. */
    @Autowired
    private EventShowModelMapper eventShowModelMapper;

    /** The event venue validation service. */
    @Autowired
    private EventVenueValidationService eventVenueValidationService;

    /** The external api handler service. */
    @Autowired
    private EventVenueExternalApiHandlerService externalApiHandlerService;

    /** The auditorium service. */
    @Autowired
    private AuditoriumService auditoriumService;

    /**
     * Adds the event show.
     *
     * @param eventShowCreationDto the event show details dto
     * @return the event show
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventShowDetailsDto add(@NotNull(
        message = "The event show details cannot be null."
    ) final EventShowCreationDto eventShowCreationDto) {

        validate(eventShowCreationDto);

        final EventDetailsDto eventDetailsDto = externalApiHandlerService.findEventDetails(eventShowCreationDto.getEventId());

        final LocalDateTime eventShowStartDateTime = eventShowStartDateTime(eventShowCreationDto);
        final LocalDateTime eventShowEndDateTime = eventShowEndDateTime(eventDetailsDto, eventShowStartDateTime);

        validateEventShowDate(eventShowCreationDto, eventDetailsDto);

        final Auditorium auditorium = auditoriumService.findByEventVenueAndAuditoriumName(eventShowCreationDto.getEventId(),
            eventShowCreationDto.getAuditoriumName());

        validateAuditoriumSlotForShow(eventShowStartDateTime, eventShowEndDateTime, auditorium);

        final EventShow eventShow = eventShowRepository.save(eventShowModelMapper.toEntity((EventShowDto.of(eventShowCreationDto,
            auditorium.getId(), eventShowEndDateTime))));

        if (eventShow == null) {
            throw EventShowServiceException.nonLocalizedServiceException("Failed to add event show.");
        }

        return EventShowDetailsDto.of(eventShowModelMapper.toDto(eventShow), eventShowCreationDto.getEventVenueId(), auditorium
            .getName());
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(@NotNull(message = "The event show id cannot be null.") final Long id) {

        validateExistsById(id);
        eventShowRepository.deleteById(id);
    }

    /**
     * Search.
     *
     * @param eventShowSearchCriteria the event show search criteria
     * @return the event show details dto list
     */
    public EventShowDetailsDtoList search(final EventShowSearchCriteria eventShowSearchCriteria) {

        final List<EventShow> eventShows = eventShowRepository.findByEventShowSearchCriteria(
            eventShowSearchCriteria.getCityId(),
            eventShowSearchCriteria.getEventId(),
            StringUtils.isNotBlank(eventShowSearchCriteria.getDate())
                ? LocalDateTimeFormatUtil.parseLocalDate(eventShowSearchCriteria.getDate())
                : null);

        if (CollectionUtils.isEmpty(eventShows)) {
            throw EventShowServiceException.nonLocalizedServiceException("No event shows found.");
        }

        return toEventShowDetailsDtoList(eventShows);
    }

    /**
     * Finds event shows by city id.
     *
     * @param cityId the city id
     * @return the event show details dto list
     */
    public EventShowDetailsDtoList findByCityId(@NotNull(message = "The city id cannot be null.") final Long cityId) {

        final List<EventShow> eventShows = eventShowRepository.findByCityId(cityId);

        if (CollectionUtils.isEmpty(eventShows)) {
            throw EventShowNotFoundException.forCityId(cityId);
        }

        return toEventShowDetailsDtoList(eventShows);
    }

    /**
     * Finds the detailed info by id.
     *
     * @param id the id
     * @return the event show detailed info dto
     */
    public EventShowDetailedInfoDto findDetailedInfoById(@NotNull(message = "The event show id cannot be null.") final Long id) {

        return findDetailsForEventShow(findEventShowDtoById(id));

    }

    /**
     * Finds the event show by id.
     *
     * @param id the id
     * @return the event show dto
     */
    public EventShowDetailsDto findById(@NotNull(message = "The event show id cannot be null.") final Long id) {

        return toEventShowDetailsDto(findEventShowDtoById(id));

    }

    /**
     * Finds the event show by id.
     *
     * @param id the id
     * @return the event show
     */
    public EventShow findEventShowById(@NotNull(message = "The event show id cannot be null.") final Long id) {

        return eventShowRepository.findById(id).orElseThrow(() -> EventShowNotFoundException.forId(id));
    }

    /**
     * Finds the event show dto by id.
     *
     * @param id the id
     * @return the event show dto
     */
    private EventShowDto findEventShowDtoById(final Long id) {

        return eventShowModelMapper.toDto(findEventShowById(id));
    }

    /**
     * Validate exists by id.
     *
     * @param id the id
     */
    private void validateExistsById(final Long id) {

        if (!eventShowRepository.existsById(id)) {
            throw EventShowNotFoundException.forId(id);
        }
    }

    /**
     * To event show details dto list.
     *
     * @param eventShows the event shows
     * @return the event show details dto list
     */
    private EventShowDetailsDtoList toEventShowDetailsDtoList(final List<EventShow> eventShows) {

        return EventShowDetailsDtoList.of(eventShows.stream().map(eventShow -> toEventShowDetailsDto(eventShowModelMapper.toDto(eventShow))).toList());
    }

    /**
     * To event show details dto.
     *
     * @param eventShowDto the event show dto
     * @return the event show details dto
     */
    private EventShowDetailsDto toEventShowDetailsDto(final EventShowDto eventShowDto) {
        final Auditorium auditorium = findAuditoriumForEventShow(eventShowDto);
        return EventShowDetailsDto.of(eventShowDto, auditorium.getEventVenue().getId(), auditorium.getName());
    }

    /**
     * Finds the details for event show.
     *
     * @param eventShowDto the event show dto
     * @return the event show detailed info dto
     */
    private EventShowDetailedInfoDto findDetailsForEventShow(final EventShowDto eventShowDto) {

        final Auditorium auditorium = findAuditoriumForEventShow(eventShowDto);
        final EventVenueDto eventVenueDto = eventVenueService.findById(auditorium.getEventVenue().getId());
        final EventDto eventDto = externalApiHandlerService.findEvent(eventShowDto.getEventId());
        final CityDto cityDto = externalApiHandlerService.findCity(eventShowDto.getCityId());
        return EventShowDetailedInfoDto.of(eventShowDto, eventDto.getName(), cityDto.getName(), eventVenueDto.getName(),
            auditorium.getName());
    }

    /**
     * Finds the auditorium for event show.
     *
     * @param eventShowDto the event show dto
     * @return the auditorium
     */
    private Auditorium findAuditoriumForEventShow(final EventShowDto eventShowDto) {

        return auditoriumService.findById(eventShowDto.getAuditoriumId());
    }

    /**
     * Event show end date time.
     *
     * @param eventDetailsDto the event details dto
     * @param eventShowDateTime the event show date time
     * @return the local date time
     */
    private LocalDateTime eventShowEndDateTime(final EventDetailsDto eventDetailsDto, final LocalDateTime eventShowDateTime) {

        return eventShowDateTime.plusMinutes(eventDetailsDto.getDurationInMinutes())
            .plusMinutes(showDurationExtraMinutes());
    }

    /**
     * Event show start date time.
     *
     * @param eventShowCreationDto the event show creation dto
     * @return the local date time
     */
    private LocalDateTime eventShowStartDateTime(final EventShowCreationDto eventShowCreationDto) {

        return LocalDateTime.of(eventShowStartDate(eventShowCreationDto), LocalDateTimeFormatUtil
            .parseLocalTime(eventShowCreationDto.getStartTime()));
    }

    /**
     * Event show start date.
     *
     * @param eventShowCreationDto the event show creation dto
     * @return the local date
     */
    private LocalDate eventShowStartDate(final EventShowCreationDto eventShowCreationDto) {

        return LocalDateTimeFormatUtil.parseLocalDate(eventShowCreationDto.getDate());
    }

    /**
     * Show duration extra minutes.
     *
     * @return the duration
     */
    private Long showDurationExtraMinutes() {

        return EnvironmentProperties.get(environment()).showEndTimeDurationExtraMinutes();
    }

    /**
     * Validate auditorium slot for show.
     *
     * @param eventShowStartDateTime the event show start date time
     * @param eventShowEndDateTime the event show end date time
     * @param auditorium the auditorium
     */
    private void validateAuditoriumSlotForShow(
            final LocalDateTime eventShowStartDateTime,
            final LocalDateTime eventShowEndDateTime,
            final Auditorium auditorium) {

        final List<EventShow> eventShowsForDate = eventShowRepository.findByAuditoriumAndDate(auditorium, eventShowStartDateTime
            .toLocalDate());

        if (CollectionUtils.isNotEmpty(eventShowsForDate)) {
            if (!eventShowsForDate.stream()
                .map(eventShowForDate -> Pair.of(LocalDateTime.of(eventShowForDate.getDate(), eventShowForDate.getStartTime()),
                    LocalDateTime.of(eventShowForDate.getEndDate(), eventShowForDate.getEndTime())))
                .allMatch(showDateTimePair -> (eventShowStartDateTime.isBefore(showDateTimePair.getFirst())
                    && eventShowEndDateTime.isBefore(showDateTimePair.getFirst()))
                    || (eventShowStartDateTime.isAfter(showDateTimePair.getSecond())
                        && eventShowEndDateTime.isAfter(showDateTimePair.getSecond())))) {
                throw EventShowServiceException.nonLocalizedServiceException("Auditorium is already booked for the time slot.");
            }
        }
    }

    /**
     * Validate event show.
     *
     * @param eventShowCreationDto the event show creation dto
     * @param eventDetailsDto the event details dto
     */
    private void validateEventShowDate(final EventShowCreationDto eventShowCreationDto, final EventDetailsDto eventDetailsDto) {

        final LocalDate eventShowDate = eventShowStartDate(eventShowCreationDto);
        final LocalDate eventReleaseDate = LocalDateTimeFormatUtil.parseLocalDate(eventDetailsDto.getReleaseDate());

        if (eventShowDate.isBefore(eventReleaseDate)) {
            throw EventShowServiceException.nonLocalizedServiceException("The event show date must be on or after the event release date: "
                + eventReleaseDate);
        }
    }

    /**
     * Validate.
     *
     * @param eventShowCreationDto the event show details dto
     */
    private void validate(final EventShowCreationDto eventShowCreationDto) {

        validateCity(eventShowCreationDto.getCityId());

        validateEventVenueId(eventShowCreationDto.getEventVenueId());

    }

    /**
     * Validate city.
     *
     * @param cityId the city id
     */
    private void validateCity(final Long cityId) {

        eventVenueValidationService.validateCity(cityId);
    }

    /**
     * Validate event venue id.
     *
     * @param eventVenueId the event venue id
     */
    private void validateEventVenueId(final Long eventVenueId) {

        eventVenueService.findById(eventVenueId);
    }

}
