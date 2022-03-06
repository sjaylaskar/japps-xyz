/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.eventshow;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyz.apps.ticketeer.event.Event;


/**
 * The event show repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface EventShowRepository extends JpaRepository<EventShow, Long> {
    @Query(
        value = "select distinct es.* from event_show es "
            + "where "
            + "(:cityId is null or es.city_id = :cityId)"
            + "and (:eventId is null or es.event_id = :eventId) "
            + "and (:eventShowDate is null or es.date = :eventShowDate) ",
        nativeQuery = true)
    public List<EventShow> findByEventShowSearchCriteria(@Param("cityId") final Long cityId,
                                                         @Param("eventId") final Long eventId,
                                                         @Param("eventShowDate") final LocalDate eventShowDate);

    /**
     * Finds the events by city id.
     *
     * @param cityId the city id
     * @return the list
     */
    @Query(value = "select distinct ev.* from event ev "
        + "join event_show es on es.event_id = ev.id and es.city_id = :cityId",
           nativeQuery = true)
    public List<Event> findEventsByCityId(@Param("cityId") final Long cityId);
}
