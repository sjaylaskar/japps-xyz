/*
 * Id: DiscountService.java 05-Mar-2022 12:43:40 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.xyz.apps.ticketeer.general.model.DtoList;
import com.xyz.apps.ticketeer.general.model.DtoListEmptyException;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.external.ApiPropertyKey;

import reactor.core.publisher.Mono;


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
    public DiscountDto add(@NotNull(message = "The discount to add cannot be null.") final DiscountDto discountDto) {

        validateDetails(discountDto);

        final Discount discountAdded = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountAdded == null) {
            throw new DiscountAddFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountAdded);
    }

    /**
     * Adds all the discounts.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    public DiscountDtoList addAll(@NotNull(
        message = "The discounts list to add cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDetails);

        final List<Discount> discountsAdded = discountRepository.saveAll(discountModelMapper.toEntities(discountDtoList.dtos()));

        if (CollectionUtils.isEmpty(discountsAdded)) {
            throw new DiscountAddFailedException(discountDtoList.dtos());
        }

        return DiscountDtoList.of(discountModelMapper.toDtos(discountsAdded));
    }

    /**
     * Updates discount.
     *
     * @param discountDto the discount dto
     * @return the discount dto
     */
    public DiscountDto update(@NotNull(message = "The discount to update cannot be null.") final DiscountDto discountDto) {

        validateDiscountIdNotNull(discountDto);

        validateDiscountExists(discountDto);

        validateDetails(discountDto);

        final Discount discountUpdated = discountRepository.save(discountModelMapper.toEntity(discountDto));

        if (discountUpdated == null) {
            throw new DiscountUpdateFailedException(Arrays.asList(discountDto));
        }

        return discountModelMapper.toDto(discountUpdated);

    }

    /**
     * Updates all discount.
     *
     * @param discountDtoList the discount dto list
     * @return the discount dto list
     */
    public DiscountDtoList updateAll(@NotNull(
        message = "The discounts list to update cannot be null."
    ) final DiscountDtoList discountDtoList) {

        if (DtoList.isEmpty(discountDtoList)) {
            throw new DtoListEmptyException("The discount list to add cannot be null or empty.");
        }

        discountDtoList.dtos().stream().forEach(this::validateDiscountIdNotNull);

        discountDtoList.dtos().stream().forEach(this::validateDiscountExists);

        discountDtoList.dtos().stream().forEach(this::validateDetails);

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
    public void deleteById(@NotNull(message = "The discount id to delete cannot be null.") final Long id) {

        validateDiscountExistsById(id);

        discountRepository.deleteById(id);
    }

    /**
     * Validate discount id not null.
     *
     * @param discountDto the discount dto
     * @return the long
     */
    private Long validateDiscountIdNotNull(final DiscountDto discountDto) {

        return Objects.requireNonNull(discountDto.getId(),
            "The discount id to update cannot be null");
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
    private void validateDiscountExistsById(final Long id) {

        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException(id);
        }
    }

    /**
     * Finds the by id.
     *
     * @param id the id
     * @return the discount dto
     */
    public DiscountDto findById(@NotNull(message = "The discount id to delete cannot be null.") final Long id) {
        return discountModelMapper.toDto(discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id)));
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
            throw new DiscountNotFoundException(offerCode);
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
            serviceBeansFetcher().webClientBuilder().build().get().uri(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get(cityId))).retrieve()
            .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                      response -> Mono.error(new DiscountServiceException("Invalid city id: " + cityId)));
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
            serviceBeansFetcher().webClientBuilder().build().get().uri(serviceBeansFetcher().environment().getProperty(ApiPropertyKey.GET_EVENT_VENUE_BY_ID.get(eventVenueId))).retrieve()
            .onStatus(status -> HttpStatus.FOUND.value() != status.value(),
                      response -> Mono.error(new DiscountServiceException("Invalid event venue id: " + eventVenueId)));
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
