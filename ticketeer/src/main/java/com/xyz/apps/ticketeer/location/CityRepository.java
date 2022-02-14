/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The city repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Finds the city by code.
     *
     * @param code the code
     * @return the city
     */
    City findByCode(final String code);

    /**
     * Finds the by name.
     *
     * @param name the name
     * @return the city
     */
    City findByName(final String name);

    /**
     * Finds the cities by country.
     *
     * @param countryId the country id
     * @return the list of cities
     */
    List<City> findByCountry(final Long countryId);

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional
    void deleteByCode(String code);

    /**
     * Finds the by country code.
     *
     * @param countryCode the country code
     * @return the list
     */
    List<City> findByCountryCode(String countryCode);

}
