/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

/**
 * The event service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class EventService {

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

    /** The mongo template. */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Adds the event.
     *
     * @param event the event
     * @return the event
     */
    public Event add(final Event event) {
        return eventRepository.save(event);
    }

    /**
     * Adds all events.
     *
     * @param events the events
     * @return the list of events
     */
    public List<Event> addAll(final List<Event> events) {
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
     * Adds the event.
     *
     * @param eventDetailsDto the event details dto
     * @return the event details dto
     */
    @Transactional(rollbackOn = {Throwable.class})
    public EventDetailsDto add(@NotNull(message = "Event details to add cannot be null")
    final  EventDetailsDto eventDetailsDto) {
        final Event event = add(eventModelMapper.toEntity(EventDto.of(eventDetailsDto)));
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
     * Adds multiple events.
     *
     * @param eventDetailsDtoList the event details dto list
     * @return the event details dto list
     */
    @Transactional(rollbackOn = {Throwable.class})
    public EventDetailsDtoList addAll(@NotNull(message = "Event details list to add cannot be null or empty") final EventDetailsDtoList eventDetailsDtoList) {
        final List<Event> events = addAll(eventModelMapper.toEntities(eventDetailsDtoList.dtos().stream().map(EventDto::of).toList()));
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
        return toEventDetailsDtoList(mongoTemplate.find(new Query().addCriteria(Criteria.where("eventId").in(events.stream().map(Event::getId).toList())), EventDetails.class));
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
        List<EventDetails> eventDetailsList = mongoTemplate.find(SearchQuery.scoreSortedPageableQuery(SearchQuery.phrase(text), pageNumber, pageSize), EventDetails.class);

        if (CollectionUtils.isEmpty(eventDetailsList)) {
            eventDetailsList = mongoTemplate.find(SearchQuery.scoreSortedPageableQuery(SearchQuery.any(text), pageNumber, pageSize), EventDetails.class);
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
}
