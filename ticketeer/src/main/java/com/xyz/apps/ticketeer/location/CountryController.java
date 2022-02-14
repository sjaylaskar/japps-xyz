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
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestBody final CountryDto countryDto) {
        log.info("CountryDto: " + countryDto);
        final Country country = countryModelMapper.toEntity(countryDto);
        countryService.delete(country);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable final Long id) {
        log.info("CountryDto id: " + id);
        countryService.deleteById(id);
        log.info("Country deleted.");
    }

    /**
     * Gets the country by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping(value = "/{id}")
    public CountryDto getById(@PathVariable("id") final Long id) {
        return countryModelMapper.toDto(countryService.findById(id));
    }

    /**
     * Gets the by code.
     *
     * @param code the code
     * @return the by code
     */
    @GetMapping(value = "/{code}")
    public CountryDto getByCode(@PathVariable("id") final String code) {
        return countryModelMapper.toDto(countryService.findByCode(code));
    }

    /**
     * Gets the by name.
     *
     * @param name the name
     * @return the by name
     */
    @GetMapping(value = "/{name}")
    public CountryDto getByName(@PathVariable("id") final String name) {
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
