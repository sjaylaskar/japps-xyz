/*
* Id: EventService.java 15-Feb-2022 11:21:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
