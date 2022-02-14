/*
* Id: CityController.java 14-Feb-2022 3:12:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.location;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * The city controller.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("city")
@Log4j2
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityModelMapper cityModelMapper;

    /**
     * Adds the city.
     *
     * @param cityDto the city dto
     * @return the city dto
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityDto> add(@RequestBody final CityDto cityDto) {
        log.info("CityDto: " + cityDto);
        final City city = cityModelMapper.toEntity(cityDto);
        final City cityAdded = cityService.add(city);
        log.info("City added: " + cityAdded);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityModelMapper.toDto(cityAdded));
    }

    /**
     * Adds multiple.
     *
     * @param cityDtos the city dtos
     * @return the list of cities
     */
    @PostMapping("/add/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityDtoList> addMultiple(@RequestBody final CityDtoList cityDtoList) {
        log.info("CityDto list: " + cityDtoList);
        final List<City> cities = cityModelMapper.toEntities(cityDtoList.getCityDtos());
        final List<City> citiesAdded = cityService.addAll(cities);
        log.info("Cities added: " + citiesAdded);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CityDtoList(cityModelMapper.toDtos(citiesAdded)));
    }

    /**
     * Updates the city.
     *
     * @param cityDto the city dto
     * @return the city dto
     */
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CityDto> update(@RequestBody final CityDto cityDto) {
        log.info("CityDto: " + cityDto);
        final City city = cityModelMapper.toEntity(cityDto);
        final City cityUpdated = cityService.update(city);
        log.info("City updated: " + cityUpdated);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(cityModelMapper.toDto(cityUpdated));
    }

    /**
     * Delete.
     *
     * @param cityDto the city dto
     */
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestBody final CityDto cityDto) {
        log.info("CityDto: " + cityDto);
        final City city = cityModelMapper.toEntity(cityDto);
        cityService.delete(city);
        log.info("City deleted: " + cityDto);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable final Long id) {
        log.info("CityDto id: " + id);
        cityService.deleteById(id);
        log.info("City deleted: " + id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @DeleteMapping("/delete/code/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteByCode(@PathVariable final String code) {
        log.info("CityDto code: " + code);
        cityService.deleteByCode(code);
        log.info("City deleted: " + code);
    }

    /**
     * Gets the city by id.
     *
     * @param id the id
     * @return the city by id
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CityDto getById(@PathVariable("id") final Long id) {
        return cityModelMapper.toDto(cityService.findById(id));
    }

    /**
     * Gets the city by code.
     *
     * @param code the code
     * @return the city by code
     */
    @GetMapping(value = "code/{code}")
    public CityDto getByCode(@PathVariable("code") final String code) {
        return cityModelMapper.toDto(cityService.findByCode(code));
    }

    /**
     * Gets the city by name.
     *
     * @param name the name
     * @return the city by name
     */
    @GetMapping(value = "name/{name}")
    public CityDto getByName(@PathVariable("name") final String name) {
        return cityModelMapper.toDto(cityService.findByName(name));
    }

    /**
     * All.
     *
     * @return the list
     */
    @GetMapping("/all")
    public List<CityDto> all() {
       return cityService.findAll().stream().map(cityModelMapper::toDto).collect(Collectors.toList());
    }
}
