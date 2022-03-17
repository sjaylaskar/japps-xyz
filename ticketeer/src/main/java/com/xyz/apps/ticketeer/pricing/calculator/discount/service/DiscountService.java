/*
 * Id: DiscountService.java 05-Mar-2022 12:43:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
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

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountContract;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountCreationDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDtoList;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountRepository;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountStrategy;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.ShowTimeType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.resources.Messages;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountNotFoundException.OfferCode;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.modelmapper.DiscountCreationModelMapper;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.modelmapper.DiscountModelMapper;
import com.xyz.apps.ticketeer.util.MessageUtil;


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

    /** The discount creation model mapper. */
    @Autowired
    private DiscountCreationModelMapper discountCreationModelMapper;

    /** The discount external api handler service. */
    @Autowired
    private DiscountExternalApiHandlerService discountExternalApiHandlerService;

    /**
     * Adds the discount.
     *
     * @param discountCreationDto the discount dto
     * @return the discount dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDto add(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_ADD) final DiscountCreationDto discountCreationDto) {

        discountExternalApiHandlerService.validateApplicableCityIds(discountCreationDto.getApplicableCityIds());

        discountExternalApiHandlerService.validateApplicableEventVenueIds(discountCreationDto.getApplicableEventVenueIds());

        validateOfferCode(discountCreationDto.getOfferCode());

        acceptDiscountValidator(discountCreationDto);

        final Discount discountAdded = discountRepository.save(discountCreationModelMapper.toEntity(discountCreationDto));

        if (discountAdded == null) {
            throw new DiscountAddFailedException(Arrays.asList(discountCreationDto));
        }

        return discountModelMapper.toDto(discountAdded);
    }

    /**
     * Validate offer code.
     *
     * @param offerCode the offer code
     */
    private void validateOfferCode(final String offerCode) {

        if (StringUtils.isBlank(offerCode)) {
            throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_BLANK_OFFER_CODE);
        }
        if (findDiscountByOfferCode(offerCode) != null) {
            throw new DiscountOfferCodeAlreadyExistsException(offerCode);
        }
    }

    /**
     * Adds all the discounts.
     *
     * @param discountCreationDtoList the discount dto list
     * @return the discount dto list
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDtoList addAll(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_ADD) final DiscountCreationDtoList discountCreationDtoList) {

        if (DtoList.isEmpty(discountCreationDtoList)) {
            throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_ADD);
        }

        discountCreationDtoList.dtos().stream().map(DiscountCreationDto::getApplicableCityIds).forEach(applicableCityIds -> discountExternalApiHandlerService.validateApplicableCityIds(applicableCityIds));
        discountCreationDtoList.dtos().stream().map(DiscountCreationDto::getApplicableEventVenueIds).forEach(applicableEventVenueIds -> discountExternalApiHandlerService.validateApplicableEventVenueIds(applicableEventVenueIds));
        validateOfferCodeUniqueForAll(discountCreationDtoList.dtos().stream().map(DiscountCreationDto::getOfferCode).collect(Collectors.toSet()).size(), discountCreationDtoList.size());
        discountCreationDtoList.dtos().stream().map(DiscountCreationDto::getOfferCode).forEach(this::validateOfferCode);
        discountCreationDtoList.dtos().stream().forEach(consumeDiscountValidator());

        final List<Discount> discountsAdded = discountRepository.saveAll(discountCreationModelMapper.toEntities(discountCreationDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsAdded)) {
            throw new DiscountAddFailedException(discountCreationDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsAdded));
    }


    /**
     * Validate offer code unique for all.
     *
     * @param numberOfUniqueOfferCodes the number of unique offer codes
     * @param numberOfDiscounts the number of discounts
     */
    private void validateOfferCodeUniqueForAll(final int numberOfUniqueOfferCodes, final int numberOfDiscounts) {
        if (numberOfUniqueOfferCodes != numberOfDiscounts) {
            throw new DiscountServiceException(Messages.MESSAGE_ERROR_UNIQUE_DISCOUNT_OFFER_CODES);
        }
    }

    /**
     * Updates discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    @Transactional(rollbackFor = {Throwable.class})
    public DiscountDto update(@NotNull(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_REQUIRED_DISCOUNT_UPDATE) final DiscountDto discountDto) {

        validateDiscountIdNotNull(discountDto);

        validateDiscountExists(discountDto);

        discountExternalApiHandlerService.validateApplicableCityIds(discountDto.getApplicableCityIds());

        discountExternalApiHandlerService.validateApplicableEventVenueIds(discountDto.getApplicableEventVenueIds());

        validateOfferCodeForUpdate(discountDto);

        acceptDiscountValidator(discountDto);

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
            throw new DiscountOfferCodeAlreadyExistsException(discountDto.getOfferCode());
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
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_UPDATE
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_EMPTY_DISCOUNTS_UPDATE);
        }

        discountDtoList.dtos().stream().forEach(this::validateDiscountIdNotNull);

        discountDtoList.dtos().stream().forEach(this::validateDiscountExists);

        discountDtoList.dtos().stream().map(DiscountDto::getApplicableCityIds).forEach(applicableCityIds -> discountExternalApiHandlerService.validateApplicableCityIds(applicableCityIds));
        discountDtoList.dtos().stream().map(DiscountDto::getApplicableEventVenueIds).forEach(applicableEventVenueIds -> discountExternalApiHandlerService.validateApplicableEventVenueIds(applicableEventVenueIds));

        validateOfferCodeUniqueForAll(discountDtoList.dtos().stream().map(DiscountDto::getOfferCode).collect(Collectors.toSet()).size(), discountDtoList.size());

        discountDtoList.dtos().stream().forEach(this::validateOfferCodeForUpdate);

        discountDtoList.dtos().stream().forEach(consumeDiscountValidator());

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
    public void deleteByOfferCode(@NotBlank(
        message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_DISCOUNT_OFFER_CODE_DELETE
    ) final String offerCode) {

        final Discount discount = findDiscountByOfferCode(offerCode);

        if (discount == null) {
            throw new DiscountNotFoundException(new OfferCode(offerCode));
        }

        discountRepository.delete(discount);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteById(@NotBlank(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_DELETE) final String id) {

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
            throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_UPDATE);
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
    public DiscountDto findById(@NotBlank(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_DISCOUNT_ID_FIND) final String id) {

        return discountModelMapper.toDto(discountRepository.findById(new ObjectId(id)).orElseThrow(
            () -> new DiscountNotFoundException(id)));
    }

    /**
     * Finds the by offer code.
     *
     * @param offerCode the offer code
     * @return the discount dto
     */
    public DiscountDto findByOfferCode(@NotBlank(message = MessageUtil.METHOD_ARG_VALIDATION_MESSAGE_KEY_PREFIX + Messages.MESSAGE_ERROR_NOT_BLANK_OFFER_CODE) final String offerCode) {

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

        return mongoTemplate().findOne(new Query().addCriteria(Criteria.where("offerCode").is(offerCode)),
            Discount.class);
    }

    /**
     * Finds by city.
     *
     * @param cityId the city id
     * @return the discount dto list
     */
    public DiscountDtoList findByCityId(final Long cityId) {

        final List<Discount> discounts = mongoTemplate().find(new Query().addCriteria(Criteria.where(
            "applicableCityIds").in(cityId)), Discount.class);

        if (CollectionUtils.isNotEmpty(discounts)) {
            return DiscountDtoList.of(discountModelMapper.toDtos(discounts));
        }

        throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNTS_FOR_CITY, cityId);
    }

    /**
     * Finds the by event venue id.
     *
     * @param eventVenueId the event venue id
     * @return the discount dto list
     */
    public DiscountDtoList findByEventVenueId(final Long eventVenueId) {

        final List<Discount> discounts = mongoTemplate().find(new Query().addCriteria(Criteria.where(
            "applicableEventVenueIds").in(eventVenueId)), Discount.class);

        if (CollectionUtils.isNotEmpty(discounts)) {
            return DiscountDtoList.of(discountModelMapper.toDtos(discounts));
        }

        throw new DiscountServiceException(Messages.MESSAGE_ERROR_NOT_FOUND_DISCOUNTS_FOR_EVENT_VENUE, eventVenueId);
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
     * To discount.
     *
     * @param discountDto the discount dto
     * @return the discount
     */
    public Discount toDiscount(final DiscountDto discountDto) {

        return discountModelMapper.toEntity(discountDto);
    }

    /**
     * @return
     */
    private Consumer<? super DiscountContract> consumeDiscountValidator() {

        return this::acceptDiscountValidator;
    }

    /**
     * @param discountContract
     */
    private void acceptDiscountValidator(final DiscountContract discountContract) {

        DiscountStrategy.of(discountContract.getDiscountStrategy()).accept(new DiscountValidator(discountContract));
    }
}
