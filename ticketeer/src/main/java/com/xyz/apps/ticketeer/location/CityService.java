/*
 * Id: CityService.java 14-Feb-2022 2:16:34 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The city service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
public class CityService {

    /** The city repository. */
    @Autowired
    private CityRepository cityRepository;

    /**
     * Adds the city.
     *
     * @param city the city
     * @return the city
     */
    public City add(final City city) {

        return cityRepository.save(city);
    }

    /**
     * Adds all.
     *
     * @param cities the cities
     * @return the list of cities.
     */
    public List<City> addAll(final List<City> cities) {
        return cityRepository.saveAll(cities);
    }

    /**
     * Updates the city.
     *
     * @param city the city
     * @return the city
     */
    public City update(final City city) {

        return cityRepository.save(city);
    }

    /**
     * Delete.
     *
     * @param city the city
     */
    public void delete(final City city) {

        cityRepository.delete(city);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(final Long id) {

        cityRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    public void deleteByCode(final String code) {

        cityRepository.deleteByCode(code);
    }

    /**
     * Finds the city by id.
     *
     * @param id the id
     * @return the city
     */
    public City findById(final Long id) {

        return cityRepository.findById(id).orElse(null);
    }

    /**
     * Finds the city by code.
     *
     * @param code the code
     * @return the city
     */
    public City findByCode(final String code) {

        return cityRepository.findByCode(code);
    }

    /**
     * Finds the city by name.
     *
     * @param name the name
     * @return the city
     */
    public City findByName(final String name) {

        return cityRepository.findByName(name);
    }

    /**
     * Finds the cities by country.
     *
     * @param country the country
     * @return the list of cities
     */
    public List<City> findByCountry(final Country country) {

        return cityRepository.findByCountry(country);
    }

    /**
     * Finds all cities.
     *
     * @return the list
     */
    public List<City> findAll() {

        return cityRepository.findAll();
    }
}
