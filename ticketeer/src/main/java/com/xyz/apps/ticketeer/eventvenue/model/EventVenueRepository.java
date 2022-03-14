/*
* Id: EventVenueRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The event venue repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface EventVenueRepository extends JpaRepository<EventVenue, Long> {

    /**
     * Finds the by city id.
     *
     * @param cityId the city id
     * @return the list of event venues by city id.
     */
    public List<EventVenue> findByCityId(final Long cityId);
}
