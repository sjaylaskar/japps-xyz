/*
* Id: CountryController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


/**
 * The country controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("country")
@Log4j2
public class CountryController {

    /** The country service. */
    @Autowired
    private CountryService countryService;

    /** The country model mapper. */
    @Autowired
    private CountryModelMapper countryModelMapper;

    /**
     * Adds the country.
     *
     * @param countryDto the country dto
     * @return the country dto
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CountryDto add(@RequestBody final CountryDto countryDto) {
        log.info("CountryDto: " + countryDto);
        final Country country = countryModelMapper.toEntity(countryDto);
        final Country countryAdded = countryService.add(country);
        log.info("Country added: " + countryAdded);
        return countryModelMapper.toDto(countryAdded);
    }

    /**
     * Adds multiple.
     *
     * @param countryDtos the country dtos
     * @return the list of countries
     */
    @PostMapping("/add/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CountryDto> addMultiple(@RequestBody final List<CountryDto> countryDtos) {
        log.info("CountryDtos: " + countryDtos);
        final List<Country> countries = countryModelMapper.toEntities(countryDtos);
        final List<Country> countriesAdded = countryService.addAll(countries);
        log.info("Countries added: " + countriesAdded);
        return countryModelMapper.toDtos(countriesAdded);
    }

    /**
     * Updates the country.
     *
     * @param countryDto the country dto
     * @return the country dto
     */
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public CountryDto update(@RequestBody final CountryDto countryDto) {
        log.info("CountryDto: " + countryDto);
        final Country country = countryModelMapper.toEntity(countryDto);
        final Country countryUpdated = countryService.update(country);
        log.info("Country updated: " + countryUpdated);
        return countryModelMapper.toDto(countryUpdated);
    }

    /**
     * Delete.
     *
     * @param countryDto the country dto
     */
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestBody final CountryDto countryDto) {
        log.info("CountryDto: " + countryDto);
        final Country country = countryModelMapper.toEntity(countryDto);
        countryService.delete(country);
        log.info("Country deleted: " + countryDto);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable final Long id) {
        log.info("CountryDto id: " + id);
        countryService.deleteById(id);
        log.info("Country deleted: " + id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteByCode(@PathVariable final String code) {
        log.info("CountryDto code: " + code);
        countryService.deleteByCode(code);
        log.info("Country deleted: " + code);
    }

    /**
     * Delete by name.
     *
     * @param name the name
     */
    @DeleteMapping("/delete/name/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteByName(@PathVariable final String name) {
        log.info("CountryDto name: " + name);
        countryService.deleteByName(name);
        log.info("Country deleted: " + name);
    }

    /**
     * Gets the country by id.
     *
     * @param id the id
     * @return the country by id
     */
    @GetMapping(value = "/{id}")
    public CountryDto getById(@PathVariable("id") final Long id) {
        return countryModelMapper.toDto(countryService.findById(id));
    }

    /**
     * Gets the country by code.
     *
     * @param code the code
     * @return the country by code
     */
    @GetMapping(value = "code/{code}")
    public CountryDto getByCode(@PathVariable("code") final String code) {
        return countryModelMapper.toDto(countryService.findByCode(code));
    }

    /**
     * Gets the country by name.
     *
     * @param name the name
     * @return the country by name
     */
    @GetMapping(value = "name/{name}")
    public CountryDto getByName(@PathVariable("name") final String name) {
        return countryModelMapper.toDto(countryService.findByName(name));
    }

    /**
     * All.
     *
     * @return the list
     */
    @GetMapping("/all")
    public List<CountryDto> all() {
       return countryService.findAll().stream().map(countryModelMapper::toDto).collect(Collectors.toList());
    }
}
