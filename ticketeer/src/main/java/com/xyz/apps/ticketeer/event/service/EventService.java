/*
 * Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.event.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.event.api.external.ExternalApiUrls;
import com.xyz.apps.ticketeer.event.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.event.api.external.contract.EventShowDtoList;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsCreationDto;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsCreationDtoList;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDto;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDetailsDtoList;
import com.xyz.apps.ticketeer.event.api.internal.contract.EventDto;
import com.xyz.apps.ticketeer.event.model.Event;
import com.xyz.apps.ticketeer.event.model.EventDetails;
import com.xyz.apps.ticketeer.event.model.EventDetailsRepository;
import com.xyz.apps.ticketeer.event.model.EventRepository;
import com.xyz.apps.ticketeer.event.resources.Messages;
import com.xyz.apps.ticketeer.event.service.modelmapper.EventDetailsModelMapper;
import com.xyz.apps.ticketeer.event.service.modelmapper.EventModelMapper;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.general.service.ServiceUtil;
import com.xyz.apps.ticketeer.util.MessageUtil;


/**
 * The event service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class EventService extends GeneralService {

    /** The event repository. */
    @Autowired
    private EventRepository eventRepository;

    /** The event details repository. */
    @Autowired
    private EventDetailsRepository eventDetailsRepository;

    /** The event model mapper. */
    @Autowired
    private EventModelMapper eventModelMapper;

    /** The event details model mapper. */
    @Autowired
    private EventDetailsModelMapper eventDetailsModelMapper;

    /**
     * Adds the event.
     *
     * @param eventDetailsCreationDto the event details dto
     * @return the event details dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDto add(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_EVENT_DETAILS
    ) final EventDetailsCreationDto eventDetailsCreationDto) {

        final Event event = save(eventModelMapper.toEntity(EventDto.of(eventDetailsCreationDto)));
        if (event == null) {
            throw new EventAddFailedException(Arrays.asList(eventDetailsCreationDto));
        }
        final EventDetailsDto eventDetailsDto = EventDetailsDto.of(eventDetailsCreationDto);
        eventDetailsDto.setEventId(event.getId());
        final EventDetails eventDetails = eventDetailsRepository.save(eventDetailsModelMapper.toEntity(eventDetailsDto));
        if (eventDetails == null) {
            throw new EventAddFailedException(Arrays.asList(eventDetailsCreationDto));
        }
        return eventDetailsModelMapper.toDto(eventDetails);
    }

    /**
     * Adds multiple events.
     *
     * @param eventCreationDtoList the event creation dto list
     * @return the event details dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDtoList addAll(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_EVENT_DETAILS_LIST
    ) final EventDetailsCreationDtoList eventCreationDtoList) {

        final List<Event> events = saveAll(eventModelMapper.toEntities(eventCreationDtoList.dtos().stream().map(EventDto::of)
            .toList()));

        if (CollectionUtils.isEmpty(events)) {
            throw new EventAddFailedException(eventCreationDtoList.dtos());
        }
        final List<EventDetailsDto> eventDetailsDtos = eventCreationDtoList.dtos().stream().map(EventDetailsDto::of).toList();
        final Iterator<Event> eventIterator = events.iterator();
        eventDetailsDtos.forEach(eventDetailsDto -> eventDetailsDto.setEventId(eventIterator.next().getId()));
        final List<EventDetails> eventDetails = eventDetailsRepository.saveAll(eventDetailsModelMapper.toEntities(
            eventDetailsDtos));
        if (CollectionUtils.isEmpty(eventDetails)) {
            throw new EventAddFailedException(eventCreationDtoList.dtos());
        }
        return toEventDetailsDtoList(eventDetails);
    }

    /**
     * Updates the.
     *
     * @param eventDetailsDto the event details dto
     * @return the event details dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDto update(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_EVENT_DETAILS_LIST
    ) final EventDetailsDto eventDetailsDto) {

        if (eventDetailsDto.getEventId() == null) {
            throw new EventServiceException(Messages.MESSAGE_ERROR_NOT_NULL_EVENT_ID);
        }

        if (!eventRepository.existsById(eventDetailsDto.getEventId())) {
            throw EventNotFoundException.forId(eventDetailsDto.getEventId());
        }

        final Event event = save(eventModelMapper.toEntity(EventDto.of(eventDetailsDto)));
        if (event == null) {
            throw new EventUpdateFailedException(Arrays.asList(eventDetailsDto));
        }
        eventDetailsDto.setEventId(event.getId());
        final EventDetails eventDetails = findEventDetails(eventDetailsDto.getEventId());
        if (eventDetails != null) {
            final EventDetails eventDetailsFromDto = eventDetailsModelMapper.toEntity(eventDetailsDto);
            eventDetailsFromDto.setId(eventDetails.getId());
            final EventDetails eventDetailsSaved = eventDetailsRepository.save(eventDetailsFromDto);
            if (eventDetailsSaved == null) {
                throw new EventUpdateFailedException(Arrays.asList(eventDetailsDto));
            }
            return eventDetailsModelMapper.toDto(eventDetailsSaved);
        } else {
            return saveEventDetails(eventDetailsDto);
        }
    }

    /**
     * Delete.
     *
     * @param eventId the event id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_EVENT_ID
    ) final Long eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw EventNotFoundException.forId(eventId);
        }

        final EventDetails eventDetails = findEventDetails(eventId);
        if (eventDetails != null) {
            eventDetailsRepository.deleteById(eventDetails.getId());
        }
        eventRepository.deleteById(eventId);
    }

    /**
     * Finds all events.
     *
     * @return the list
     */
    public EventDetailsDtoList findAll() {

        final List<EventDetails> eventDetails = eventDetailsRepository.findAll();
        if (CollectionUtils.isEmpty(eventDetails)) {
            throw EventNotFoundException.noneFound();
        }
        return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetails));
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the event dto
     */
    public EventDto findById(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_EVENT_ID
    ) final Long id) {

        return eventModelMapper.toDto(eventRepository.findById(id).orElseThrow(() -> EventNotFoundException.forId(id)));
    }

    /**
     * Finds the event details by event id.
     *
     * @param eventId the event id
     * @return the event details dto
     */
    public EventDetailsDto findEventDetailsByEventId(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_EVENT_ID
    ) final Long eventId) {

        final EventDetails eventDetails = findEventDetails(eventId);
        if (eventDetails != null) {
            return eventDetailsModelMapper.toDto(eventDetails);
        }
        throw EventNotFoundException.forId(eventId);
    }

    /**
     * Finds the event details by event id.
     *
     * @param eventIds the event ids
     * @return the event details dto list
     */
    public EventDetailsDtoList findEventDetailsByEventIds(@NotEmpty(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_EMPTY_EVENT_ID_LIST
    ) final List<Long> eventIds) {

        final List<EventDetails> eventDetailsList = mongoTemplate().find(new Query().addCriteria(Criteria
            .where("eventId").in(eventIds)), EventDetails.class);
        if (CollectionUtils.isNotEmpty(eventDetailsList)) {
            return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetailsList));
        }
        throw EventNotFoundException.forIds(eventIds);
    }

    /**
     * Finds the event details by city id.
     *
     * @param cityId the city id
     * @return the event details dto list
     */
    public EventDetailsDtoList findEventDetailsByCityId(@NotNull(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_ID
    ) final Long cityId) {

        ResponseEntity<EventShowDtoList> eventShowDtoListResponseEntity = null;
        try {
            eventShowDtoListResponseEntity = restTemplate().getForEntity(
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.GET_EVENT_SHOWS_BY_CITY_ID, cityId), EventShowDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw EventNotFoundException.forCityId(cityId);
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDtoListResponseEntity) || CollectionUtils.isEmpty(eventShowDtoListResponseEntity.getBody().getDtos())) {
            throw EventNotFoundException.forCityId(cityId);
        }

        return findEventDetailsByEventIds(eventShowDtoListResponseEntity.getBody().getDtos().stream().map(EventShowDto::getEventId).toList());

    }

    /**
     * Search by text.
     *
     * @param text the text
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return the event details dto list
     */
    public EventDetailsDtoList searchByText(final String text, final int pageNumber, final int pageSize) {

        List<EventDetails> eventDetailsList = mongoTemplate().find(SearchQuery.scoreSortedPageableQuery(
            SearchQuery.phrase(text), pageNumber, pageSize), EventDetails.class);

        if (CollectionUtils.isEmpty(eventDetailsList)) {
            eventDetailsList = mongoTemplate().find(SearchQuery.scoreSortedPageableQuery(SearchQuery.any(
                text), pageNumber, pageSize), EventDetails.class);
        }

        if (CollectionUtils.isEmpty(eventDetailsList)) {
            throw EventNotFoundException.noneFound();
        }

        return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetailsList));
    }

    /**
     * Saves the event.
     *
     * @param event the event
     * @return the event
     */
    private Event save(final Event event) {

        return eventRepository.save(event);
    }

    /**
     * Saves all events.
     *
     * @param events the events
     * @return the list of events
     */
    private List<Event> saveAll(final List<Event> events) {

        return eventRepository.saveAll(events);
    }

    /**
     * Finds the event details.
     *
     * @param eventId the event id
     * @return the event details
     */
    private EventDetails findEventDetails(final Long eventId) {

        return mongoTemplate().findOne(new Query().addCriteria(Criteria.where("eventId").is(eventId)),
            EventDetails.class);
    }

    /**
     * Save event details.
     *
     * @param eventDetailsDto the event details dto
     * @return the event details dto
     */
    private EventDetailsDto saveEventDetails(final EventDetailsDto eventDetailsDto) {

        final EventDetails eventDetails = eventDetailsRepository.save(eventDetailsModelMapper.toEntity(eventDetailsDto));
        if (eventDetails == null) {
            throw new EventUpdateFailedException(Arrays.asList(eventDetailsDto));
        }
        return eventDetailsModelMapper.toDto(eventDetails);
    }

    /**
     * To event details dto list.
     *
     * @param eventDetails the event details
     * @return the event details dto list
     */
    private EventDetailsDtoList toEventDetailsDtoList(final List<EventDetails> eventDetails) {

        return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetails));
    }

    /**
     * The search query matcher.
     */
    private static final class SearchQuery {

        /**
         * Score sorted pageable query.
         *
         * @param textQuery the text query
         * @param pageNumber the page number
         * @param pageSize the page size
         * @return the query
         */
        private static Query scoreSortedPageableQuery(final TextQuery textQuery, final int pageNumber, final int pageSize) {

            return textQuery.sortByScore().includeScore()
                .with(PageRequest.of(pageNumber, pageSize).withSort(Sort.by(Order.desc("score"))));
        }

        /**
         * Phrase.
         *
         * @param text the text
         * @return the text query
         */
        private static TextQuery phrase(final String text) {

            return TextQuery
                .queryText(TextCriteria.forDefaultLanguage()
                    .matchingPhrase(text));
        }

        /**
         * Any.
         *
         * @param text the text
         * @return the text query
         */
        private static TextQuery any(final String text) {

            return TextQuery
                .queryText(TextCriteria.forDefaultLanguage()
                    .matchingAny(text.split(StringUtils.SPACE)));
        }
    }
}
