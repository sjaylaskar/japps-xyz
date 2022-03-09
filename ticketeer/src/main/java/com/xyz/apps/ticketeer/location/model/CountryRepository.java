/*
* Id: CountryRepository.java 14-Feb-2022 1:17:04 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The country repository.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Finds the Country by code.
     *
     * @param code the code
     * @return the country
     */
    @Query("select c from Country c where c.code = :code")
    Country findByCode(@Param("code") final String code);

    /**
     * Finds the Country by name.
     *
     * @param name the name
     * @return the country
     */
    @Query("select c from Country c where c.name = :name")
    Country findByName(@Param("name") final String name);

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional
    @Modifying
    @Query("delete from Country c where c.code = :code")
    void deleteByCode(@Param("code") final String code);

    /**
     * Delete by name.
     *
     * @param name the name
     */
    @Transactional
    @Modifying
    @Query("delete from Country c where c.name = :name")
    void deleteByName(@Param("name") final String name);

}
