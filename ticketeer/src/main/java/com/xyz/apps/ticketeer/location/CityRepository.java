/*
* Id: CityRepository.java 14-Feb-2022 1:18:18 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("select c from City c where c.code = :code")
    City findByCode(@Param("code") final String code);

    /**
     * Finds the cities by name.
     *
     * @param name the name
     * @return the citites
     */
    @Query("select c from City c where c.name = :name")
    List<City> findByName(@Param("name") final String name);

    /**
     * Finds the cities by country.
     *
     * @param countryId the country id
     * @return the list of cities
     */
    @Query("select c from City c where c.country = :country")
    List<City> findByCountry(final Country country);

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional
    @Modifying
    @Query("delete from City c where c.code = :code")
    void deleteByCode(@Param("code") final String code);

    /**
     * Finds the by country code.
     *
     * @param countryCode the country code
     * @return the list
     */
    @Query(value = "select distinct c.* from City c join Country cn on c.country_id = cn.id where cn.code = :countryCode",
           nativeQuery = true)
    List<City> findByCountryCode(@Param("countryCode") final String countryCode);

}
