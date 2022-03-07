/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.event.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.event.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.event.api.external.contract.EventShowDtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;

import reactor.core.publisher.Mono;

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
     * Finds all events.
     *
     * @return the list
     */
    public EventDetailsDtoList findAll() {

        return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetailsRepository.findAll()));
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the event dto
     */
    public EventDto findById(final Long id) {
        return eventModelMapper.toDto(eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id)));
    }

    /**
     * Finds the event details by event id.
     *
     * @param eventId the event id
     * @return the event details dto
     */
    public EventDetailsDto findEventDetailsByEventId(@NotNull(message = "The event id cannot be null.") final Long eventId) {
        final EventDetails eventDetails = findEventDetails(eventId);
        if (eventDetails != null) {
            return eventDetailsModelMapper.toDto(eventDetails);
        }
        throw new EventNotFoundException(eventId);
    }

    /**
     * Finds the event details.
     *
     * @param eventId the event id
     * @return the event details
     */
    private EventDetails findEventDetails(final Long eventId) {

        return serviceBeansFetcher().mongoTemplate().findOne(new Query().addCriteria(Criteria.where("eventId").is(eventId)), EventDetails.class);
    }

    /**
     * Finds the event details by event id.
     *
     * @param eventIds the event ids
     * @return the event details dto list
     */
    public EventDetailsDtoList findEventDetailsByEventIds(@NotNull(message = "The event id cannot be null.") final List<Long> eventIds) {
        final List<EventDetails> eventDetailsList = serviceBeansFetcher().mongoTemplate().find(new Query().addCriteria(Criteria.where("eventId").in(eventIds)), EventDetails.class);
        if (CollectionUtils.isNotEmpty(eventDetailsList)) {
            return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetailsList));
        }
        throw new EventNotFoundException(eventIds);
    }

    /**
     * Adds the event.
     *
     * @param eventDetailsDto the event details dto
     * @return the event details dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDto add(@NotNull(message = "Event details to add cannot be null.")
    final  EventDetailsDto eventDetailsDto) {
        final Event event = save(eventModelMapper.toEntity(EventDto.of(eventDetailsDto)));
        if (event == null) {
            throw new EventAddFailedException(Arrays.asList(eventDetailsDto));
        }
        eventDetailsDto.setEventId(event.getId());
        final EventDetails eventDetails = eventDetailsRepository.save(eventDetailsModelMapper.toEntity(eventDetailsDto));
        if (eventDetails == null) {
            throw new EventAddFailedException(Arrays.asList(eventDetailsDto));
        }
        return eventDetailsModelMapper.toDto(eventDetails);
    }

    /**
     * Updates the.
     *
     * @param eventDetailsDto the event details dto
     * @return the event details dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDto update(@NotNull(message = "Event details to update cannot be null.")
    final  EventDetailsDto eventDetailsDto) {
        if (eventDetailsDto.getEventId() == null) {
            throw new EventServiceException("Event id is required to update event.");
        }
        if (eventRepository.existsById(eventDetailsDto.getEventId())) {
            final Event event = save(eventModelMapper.toEntity(EventDto.of(eventDetailsDto)));
            if (event == null) {
                throw new EventUpdateFailedException(Arrays.asList(eventDetailsDto));
            }
            eventDetailsDto.setEventId(event.getId());
            final EventDetailsDto eventDetailsDtoForEvent = findEventDetailsByEventId(eventDetailsDto.getEventId());
            if (eventDetailsDtoForEvent != null) {
                eventDetailsDto.setId(eventDetailsDtoForEvent.getId());
                return saveEventDetails(eventDetailsDto);
            } else {
                return saveEventDetails(eventDetailsDto);
            }
        } else {
            throw new EventNotFoundException(eventDetailsDto.getEventId());
        }
    }

    /**
     * Delete.
     *
     * @param eventId the event id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(@NotNull(message = "Event id cannot be null") final Long eventId) {
        if (eventRepository.existsById(eventId)) {

            final EventDetails eventDetails = findEventDetails(eventId);
            if (eventDetails != null) {
                eventDetailsRepository.deleteById(eventDetails.getId());
            }

            eventRepository.deleteById(eventId);
        }
        throw new EventNotFoundException(eventId);
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
     * Adds multiple events.
     *
     * @param eventDetailsDtoList the event details dto list
     * @return the event details dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public EventDetailsDtoList addAll(@NotNull(message = "Event details list to add cannot be null or empty") final EventDetailsDtoList eventDetailsDtoList) {
        final List<Event> events = saveAll(eventModelMapper.toEntities(eventDetailsDtoList.dtos().stream().map(EventDto::of).toList()));
        if (CollectionUtils.isEmpty(events)) {
            throw new EventAddFailedException(eventDetailsDtoList.dtos());
        }
        final Iterator<Event> eventIterator = events.iterator();
        eventDetailsDtoList.dtos().forEach(eventDetailsDto -> eventDetailsDto.setEventId(eventIterator.next().getId()));
        final List<EventDetails> eventDetails = eventDetailsRepository.saveAll(eventDetailsModelMapper.toEntities(eventDetailsDtoList.dtos()));
        if (CollectionUtils.isEmpty(eventDetails)) {
            throw new EventAddFailedException(eventDetailsDtoList.dtos());
        }
        return toEventDetailsDtoList(eventDetails);
    }

    /**
     * Finds the all by events.
     *
     * @param events the events
     * @return the event details dto list
     */
    public EventDetailsDtoList findAllByEvents(final List<Event> events) {
        return toEventDetailsDtoList(serviceBeansFetcher().mongoTemplate().find(new Query().addCriteria(Criteria.where("eventId").in(events.stream().map(Event::getId).toList())), EventDetails.class));
    }

    /**
     * To event details dto list.
     *
     * @param eventDetails the event details
     * @return the event details dto list
     */
    public EventDetailsDtoList toEventDetailsDtoList(final List<EventDetails> eventDetails) {

        return EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetails));
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
        List<EventDetails> eventDetailsList = serviceBeansFetcher().mongoTemplate().find(SearchQuery.scoreSortedPageableQuery(SearchQuery.phrase(text), pageNumber, pageSize), EventDetails.class);

        if (CollectionUtils.isEmpty(eventDetailsList)) {
            eventDetailsList = serviceBeansFetcher().mongoTemplate().find(SearchQuery.scoreSortedPageableQuery(SearchQuery.any(text), pageNumber, pageSize), EventDetails.class);
        }
        return (CollectionUtils.isNotEmpty(eventDetailsList))
                ? EventDetailsDtoList.of(eventDetailsModelMapper.toDtos(eventDetailsList))
                : null;
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

    /**
     * Finds the event details by city id.
     *
     * @param cityId the city id
     * @return the event details dto list
     */
    public EventDetailsDtoList findEventDetailsByCityId(@NotNull(message = "The city id cannot be null.") final Long cityId) {
        final EventShowDtoList eventShowDtoList = serviceBeansFetcher().webClientBuilder().build()
        .get()
        .uri(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_SHOWS_BY_CITY_ID.get(cityId)))
        .retrieve()
        .onStatus(status -> HttpStatus.OK.value() != status.value(),
                  response -> Mono.error(new EventServiceException(response.bodyToMono(String.class).block())))
        .bodyToMono(EventShowDtoList.class).block();

        if (eventShowDtoList != null && CollectionUtils.isNotEmpty(eventShowDtoList.getDtos())) {
            return findEventDetailsByEventIds(eventShowDtoList.getDtos().stream().map(EventShowDto::getEventId).toList());
        }

        throw new EventServiceException("No events found for city: " + cityId);
    }
}
