/*
 * Id: CityService.java 14-Feb-2022 2:16:34 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.location.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityCreationDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityCreationDtoList;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityDto;
import com.xyz.apps.ticketeer.location.api.internal.contract.CityDtoList;
import com.xyz.apps.ticketeer.location.api.internal.contract.CountryDto;
import com.xyz.apps.ticketeer.location.model.City;
import com.xyz.apps.ticketeer.location.model.CityRepository;
import com.xyz.apps.ticketeer.location.resources.Messages;
import com.xyz.apps.ticketeer.location.service.modelmapper.CityCreationModelMapper;
import com.xyz.apps.ticketeer.location.service.modelmapper.CityModelMapper;
import com.xyz.apps.ticketeer.util.MessageUtil;


/**
 * The city service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class CityService extends GeneralService {

    /** The city repository. */
    @Autowired
    private CityRepository cityRepository;

    /** The country service. */
    @Autowired
    private CountryService countryService;

    /** The city model mapper. */
    @Autowired
    private CityModelMapper cityModelMapper;

    /** The city creation model mapper. */
    @Autowired
    private CityCreationModelMapper cityCreationModelMapper;

    /**
     * Adds the city.
     *
     * @param cityCreationDto the city creation dto
     * @return the city
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CityDto add(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY) final CityCreationDto cityCreationDto) {
        validateCityCreation(cityCreationDto);
        final City city = cityRepository.save(cityCreationModelMapper.toEntity(cityCreationDto));
        if (city == null) {
            throw new CityServiceException(Messages.MESSAGE_ERROR_FAILED_CITY_ADD);
        }
        return cityModelMapper.toDto(city);
    }

    /**
     * Adds all.
     *
     * @param cityCreationDtoList the city creation dto list
     * @return the list of cities.
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CityDtoList addAll(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_LIST) final CityCreationDtoList cityCreationDtoList) {
        if (DtoList.isNotEmpty(cityCreationDtoList)) {
            if (cityCreationDtoList.dtos().stream().map(CityCreationDto::getCode).collect(Collectors.toSet()).size() != cityCreationDtoList.size()) {
                throw new CityServiceException(Messages.MESSAGE_ERROR_UNIQUE_CITY_CODES);
            }
            cityCreationDtoList.dtos().stream().forEach(this::validateCityCreation);
            final List<City> cities = cityRepository.saveAll(cityCreationModelMapper.toEntities(cityCreationDtoList.dtos()));
            if (CollectionUtils.isEmpty(cities)) {
                throw new CityServiceException(Messages.MESSAGE_ERROR_FAILED_CITIES_ADD);
            }
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }

        throw new CityServiceException(Messages.MESSAGE_ERROR_NOT_NULL_CITY_LIST);
    }

    /**
     * Updates the city.
     *
     * @param cityDto the city dto
     * @return the city
     */
    @Transactional(rollbackFor = {Throwable.class})
    public CityDto update(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY) final CityDto cityDto) {
        if (cityDto.getId() == null) {
            throw new CityServiceException(Messages.MESSAGE_ERROR_NOT_NULL_CITY_ID);
        }
        if (!cityRepository.existsById(cityDto.getId())) {
            throw CityNotFoundException.forId(cityDto.getId());
        }
        final City cityByCode = cityRepository.findByCode(cityDto.getCode());
        if (cityByCode != null && cityByCode.getId() != cityDto.getId()) {
            throw new CityAlreadyExistsException(cityDto.getCode());
        }
        return cityModelMapper.toDto(cityRepository.save(cityModelMapper.toEntity(cityDto)));
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_ID) final Long id) {
        if (!cityRepository.existsById(id)) {
            throw CityNotFoundException.forId(id);
        }
        cityRepository.deleteById(id);
    }

    /**
     * Delete by code.
     *
     * @param code the code
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByCode(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_CODE) final String code) {
        final CityDto cityDto = findByCode(code);
        if (cityDto == null) {
            throw CityNotFoundException.forCode(code);
        }
        cityRepository.deleteByCode(code);
    }

    /**
     * Finds the city by id.
     *
     * @param id the id
     * @return the city
     */
    public CityDto findById(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_ID) final Long id) {
        return cityModelMapper.toDto(cityRepository.findById(id).orElseThrow(() -> CityNotFoundException.forId(id)));
    }

    /**
     * Finds the city by code.
     *
     * @param code the code
     * @return the city
     */
    public CityDto findByCode(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_CODE) final String code) {
        final City city = cityRepository.findByCode(code);
        if (city != null) {
            return cityModelMapper.toDto(city);
        }
        throw CityNotFoundException.forCode(code);
    }

    /**
     * Finds the cities by country.
     *
     * @param countryId the country id
     * @return the list of cities
     */
    public CityDtoList findByCountry(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_NULL_CITY_ID) final Long countryId) {
        final CountryDto countryDto = countryService.findById(countryId);
        final List<City> cities = cityRepository.findByCountry(countryService.toCountry(countryDto));
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }

        throw CityNotFoundException.forCountryId(countryId);
    }

    /**
     * Finds all cities.
     *
     * @return the list
     */
    public CityDtoList findAll() {
        final List<City> cities = cityRepository.findAll();
        if (CollectionUtils.isNotEmpty(cities)) {
            return CityDtoList.of(cityModelMapper.toDtos(cities));
        }
        throw new CityNotFoundException();
    }

    /**
     * Validate city creation.
     *
     * @param cityCreationDto the city creation dto
     */
    private void validateCityCreation(final CityCreationDto cityCreationDto) {
        if (cityRepository.findByCode(cityCreationDto.getCode()) != null) {
            throw new CityAlreadyExistsException(cityCreationDto.getCode());
        }

        if (countryService.findById(cityCreationDto.getCountryId()) == null) {
            throw CountryNotFoundException.forId(cityCreationDto.getCountryId());
        }
    }
}
