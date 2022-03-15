/*
* Id: AuditoriumRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The auditorium repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

    /**
     * Finds the auditoriums by event venue and name.
     *
     * @param eventVenue the event venue
     * @param name the name
     * @return the auditoriums by event venue and name.
     */
    public Auditorium findByEventVenueAndName(final EventVenue eventVenue, final String name);

    /**
     * Finds the auditoriums by event venue and names.
     *
     * @param eventVenue the event venue
     * @param names the names
     * @return the auditoriums by event venue and names
     */
    @Query("select audi from Auditorium audi where eventVenue = :eventVenue and name in :names")
    public List<Auditorium> findByEventVenueAndNames(@Param(value = "eventVenue") final EventVenue eventVenue, @Param(value = "names") final Collection<String> names);

    /**
     * Finds the by event venue and id.
     *
     * @param eventVenue the event venue
     * @param id the id
     * @return the auditorium
     */
    @Query(value = "select audi.* from Auditorium audi where audi.event_venue_id = :eventVenueId and audi.id = id", nativeQuery = true)
    public List<Auditorium> findByEventVenueIdAndId(@Param(value = "eventVenueId") final Long eventVenueId, @Param(value = "id") final Long id);

    /**
     * Finds the auditoriums by event venue.
     *
     * @param eventVenue the event venue
     * @return the auditoriums
     */
    public List<Auditorium> findByEventVenue(final EventVenue eventVenue);
}
