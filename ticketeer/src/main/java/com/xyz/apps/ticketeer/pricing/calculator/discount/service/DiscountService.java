/*
 * Id: DiscountService.java 05-Mar-2022 12:43:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.model.DtoListEmptyException;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.contract.EventVenueDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountModelMapper;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountRepository;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountNotFoundException.OfferCode;
import com.xyz.apps.ticketeer.pricing.calculator.model.ShowTimeType;
import com.xyz.apps.ticketeer.util.StringUtil;


/**
 * The discount service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Validated
@Service
public class DiscountService extends GeneralService {

    /** The discount repository. */
    @Autowired
    private DiscountRepository discountRepository;

    /** The discount model mapper. */
    @Autowired
    private DiscountModelMapper discountModelMapper;

    /**
     * Adds the discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDto add(@NotNull(message = "The discount to add cannot be null.") final DiscountDto discountDto) {

        validateDetails(discountDto);

        validateOfferCode(discountDto.getOfferCode());

        final Discount discountAdded = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountAdded == null) {
            throw new DiscountAddFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountAdded);
    }

    /**
     * Validate offer code.
     *
     * @param offerCode the offer code
     */
    private void validateOfferCode(@NotBlank(message = "The offer code cannot be blank.") final String offerCode) {
        if (StringUtils.isBlank(offerCode)) {
            throw new DiscountServiceException("Offer code cannot be blank.");
        }
        if (findDiscountByOfferCode(offerCode) != null) {
            throw InvalidOfferCodeException.offerCodeExists(offerCode);
        }
    }

    /**
     * Adds all the discounts.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDtoList addAll(@NotNull(
        message = "The discounts list to add cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDetails);
        discountDtoList.dtos().stream().map(DiscountDto::getOfferCode).forEach(this::validateOfferCode);

        validOfferCodeUniqueForAll(discountDtoList);

        final List<Discount> discountsAdded = discountRepository.saveAll(discountModelMapper.toEntities(discountDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsAdded)) {
            throw new DiscountAddFailedException(discountDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsAdded));
    }

    /**
     * Valid offer code unique for all.
     *
     * @param discountDtoList the discount dto list
     */
    private void validOfferCodeUniqueForAll(@NotNull(message = "The discounts list to add cannot be null."
    ) final DiscountDtoList discountDtoList) {
        if (discountDtoList.dtos().stream().map(DiscountDto::getOfferCode).collect(Collectors.toSet()).size() != discountDtoList.size()) {
            throw new DiscountServiceException("Discount offer codes must be unique.");
        }
    }

    /**
     * Updates discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDto update(@NotNull(message = "The discount to update cannot be null.") final DiscountDto discountDto) {

        validateDiscountIdNotNull(discountDto);

        validateDiscountExists(discountDto);

        validateDetails(discountDto);

        validateOfferCodeForUpdate(discountDto);

        final Discount discountUpdated = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountUpdated == null) {
            throw new DiscountUpdateFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountUpdated);

    }

    /**
     * @param discountDto
     */
    private void validateOfferCodeForUpdate(final DiscountDto discountDto) {

        final DiscountDto discountDtoByOfferCode = findByOfferCode(discountDto.getOfferCode());
        if (!StringUtils.equals(discountDto.getId(), discountDtoByOfferCode.getId())) {
            throw InvalidOfferCodeException.offerCodeExists(discountDto.getOfferCode());
        }
    }

    /**
     * Updates all discount.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDtoList updateAll(@NotNull(
        message = "The discounts list to update cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDiscountIdNotNull);

        discountDtoList.dtos().stream().forEach(this::validateDiscountExists);

        discountDtoList.dtos().stream().forEach(this::validateDetails);

        validOfferCodeUniqueForAll(discountDtoList);

        discountDtoList.dtos().stream().forEach(this::validateOfferCodeForUpdate);

        final List<Discount> discountsUpdated = discountRepository.saveAll(discountModelMapper.toEntities(discountDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsUpdated)) {
            throw new DiscountUpdateFailedException(discountDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsUpdated));
    }

    /**
     * Delete by offer code.
     *
     * @param offerCode the offer code
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteByOfferCode(@NotBlank(message = "The discount offer code to delete cannot be blank.") final String offerCode) {

        final Discount discount = findDiscountByOfferCode(offerCode);

        if (discount == null) {
            throw new DiscountNotFoundException(offerCode);
        }

        discountRepository.delete(discount);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotBlank(message = "The discount id to delete cannot be null.") final String id) {

        validateDiscountExistsById(id);

        discountRepository.deleteById(new ObjectId(id));
    }

    /**
     * Validate discount id not null.
     *
     * @param discountDto the discount dto
     * @return the long
     */
    private void validateDiscountIdNotNull(final DiscountDto discountDto) {

        if (StringUtils.isBlank(discountDto.getId())) {
            throw new DiscountServiceException("The discount id to update cannot be null");
        }
    }

    /**
     * Validate discount exists.
     *
     * @param discountDto the discount dto
     */
    private void validateDiscountExists(final DiscountDto discountDto) {

        validateDiscountExistsById(discountDto.getId());
    }

    /**
     * Validate discount exists by id.
     *
     * @param discountDto the discount dto
     */
    private void validateDiscountExistsById(final String id) {

        if (!discountRepository.existsById(new ObjectId(id))) {
            throw new DiscountNotFoundException(id);
        }
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the discount dto
     */
    public DiscountDto findById(@NotBlank(message = "The discount id to delete cannot be null.") final String id) {
        return discountModelMapper.toDto(discountRepository.findById(new ObjectId(id)).orElseThrow(() -> new DiscountNotFoundException(id)));
    }

    /**
     * Finds the by offer code.
     *
     * @param offerCode the offer code
     * @return the discount dto
     */
    public DiscountDto findByOfferCode(@NotBlank(message = "The offer code cannot be blank.") final String offerCode) {

        final Discount discount = findDiscountByOfferCode(offerCode);

        if (discount == null) {
            throw new DiscountNotFoundException(new OfferCode(offerCode));
        }

        return discountModelMapper.toDto(discount);
    }

    /**
     * Finds the discount by offer code.
     *
     * @param offerCode the offer code
     * @return the discount
     */
    private Discount findDiscountByOfferCode(final String offerCode) {

        return serviceBeansFetcher().mongoTemplate().findOne(new Query().addCriteria(Criteria.where("offerCode").is(offerCode)),
            Discount.class);
    }

    /**
     * Finds by city.
     *
     * @param cityId the city id
     * @return the discount dto list
     */
    public DiscountDtoList findByCityId(final Long cityId) {
        final List<Discount> discounts = serviceBeansFetcher().mongoTemplate().find(new Query().addCriteria(Criteria.where("applicableCityIds").in(cityId)), Discount.class);

        if (CollectionUtils.isNotEmpty(discounts)) {
            return DiscountDtoList.of(discountModelMapper.toDtos(discounts));
        }

        throw new DiscountServiceException("No discounts found for city: " + cityId);
    }

    /**
     * Finds the by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the discount dto list
     */
    public DiscountDtoList findByEventVenueId(final Long eventVenueId) {
        final List<Discount> discounts = serviceBeansFetcher().mongoTemplate().find(new Query().addCriteria(Criteria.where("applicableEventVenueIds").in(eventVenueId)), Discount.class);

        if (CollectionUtils.isNotEmpty(discounts)) {
            return DiscountDtoList.of(discountModelMapper.toDtos(discounts));
        }

        throw new DiscountServiceException("No discounts found for event venue: " + eventVenueId);
    }

    /**
     * Finds all.
     *
     * @return the discount dto list
     */
    public DiscountDtoList findAll() {
        final List<Discount> discounts = discountRepository.findAll();
        if (CollectionUtils.isEmpty(discounts)) {
            throw new DiscountNotFoundException();
        }
        return DiscountDtoList.of(discountModelMapper.toDtos(discounts));
    }

    /**
     * Finds the discount strategies.
     *
     * @return the list
     */
    public List<String> findDiscountStrategies() {
        return Arrays.asList(DiscountStrategy.values()).stream().map(DiscountStrategy::fullDescription).toList();
    }

    /**
     * Finds the discount types.
     *
     * @return the list
     */
    public List<String> findDiscountTypes() {
        return Arrays.asList(DiscountType.values()).stream().map(DiscountType::fullDescription).toList();
    }

    /**
     * Finds the show time types.
     *
     * @return the list
     */
    public List<String> findShowTimeTypes() {
        return Arrays.asList(ShowTimeType.values()).stream().map(ShowTimeType::fullDescription).toList();
    }

    /**
     * Validate.
     *
     * @param discountDto the discount dto
     */
    private void validateDetails(final DiscountDto discountDto) {

        if (CollectionUtils.isNotEmpty(discountDto.getApplicableCityIds())) {
            validateApplicableCityIds(discountDto.getApplicableCityIds());
        }

        if (CollectionUtils.isNotEmpty(discountDto.getApplicableEventVenueIds())) {
            validateApplicableEventVenueIds(discountDto.getApplicableEventVenueIds());
        }
    }

    /**
     * Validate applicable city ids.
     *
     * @param applicableCityIds the applicable city ids
     */
    private void validateApplicableCityIds(final Set<Long> applicableCityIds) {

        applicableCityIds
        .forEach(cityId -> {
            try {
                serviceBeansFetcher().restTemplate().getForEntity(
                    StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), cityId), CityDto.class);
            } catch (final HttpStatusCodeException exception) {
                throw new DiscountServiceException("Invalid city id: " + cityId);
            }
        });
    }

    /**
     * Validate applicable event venue ids.
     *
     * @param applicableEventVenueIds the applicable event venue ids
     */
    private void validateApplicableEventVenueIds(final Set<Long> applicableEventVenueIds) {
        applicableEventVenueIds
        .forEach(eventVenueId -> {
            try {
                serviceBeansFetcher().restTemplate().getForEntity(
                    StringUtil.format(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_VENUE_BY_ID.get()), eventVenueId), EventVenueDto.class);
            } catch (final HttpStatusCodeException exception) {
                throw new DiscountServiceException("Invalid event venue id: " + eventVenueId);
            }
        });
    }

    /**
     * To discount.
     *
     * @param discountDto the discount dto
     * @return the discount
     */
    public Discount toDiscount(final DiscountDto discountDto) {
        return discountModelMapper.toEntity(discountDto);
    }
}
