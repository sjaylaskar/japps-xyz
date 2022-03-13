/*
 * Id: CityModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.discount.model;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;
import com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract.DiscountDto;


/**
 * The discount dto mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class DiscountModelMapper extends GeneralModelMapper<Discount, DiscountDto> {

    /**
     * Instantiates a new discount model mapper.
     */
    public DiscountModelMapper() {

        super(Discount.class, DiscountDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {
        final TypeMap<Discount, DiscountDto> discountToDiscountDtoMap = modelMapper.createTypeMap(Discount.class, DiscountDto.class);
        discountToDiscountDtoMap
        .addMappings(
            mapper -> mapper.using(ModelConverter.OBJECTID_TO_STRING_CONVERTER).map(Discount::getId, DiscountDto::setId)
          )
        .addMappings(
          mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(Discount::getDiscountStrategy, DiscountDto::setDiscountStrategy)
        )
        .addMappings(
            mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(Discount::getDiscountType, DiscountDto::setDiscountType)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(Discount::getShowTimeType, DiscountDto::setShowTimeType)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(Discount::getStartTime, DiscountDto::setStartTime)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.LOCALDATETIME_TO_STRING_CONVERTER).map(Discount::getEndTime, DiscountDto::setEndTime)
          );

        final Converter<String, DiscountStrategy> stringToDiscountStrategyConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? DiscountStrategy.of(converter.getSource()) : null;
        final Converter<String, DiscountType> stringToDiscountTypeConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? DiscountType.of(converter.getSource()) : null;
        final Converter<String, ShowTimeType> stringToShowTimeTypeConverter = converter -> (StringUtils.isNotBlank(converter.getSource())) ? ShowTimeType.of(converter.getSource()) : null;
        final TypeMap<DiscountDto, Discount> discountDtoToDiscountMap = modelMapper.createTypeMap(DiscountDto.class, Discount.class);
        discountDtoToDiscountMap
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_OBJECTID_CONVERTER).map(DiscountDto::getId, Discount::setId)
          )
        .addMappings(
          mapper -> mapper.using(stringToDiscountStrategyConverter).map(DiscountDto::getDiscountStrategy, Discount::setDiscountStrategy)
        )
        .addMappings(
            mapper -> mapper.using(stringToDiscountTypeConverter).map(DiscountDto::getDiscountType, Discount::setDiscountType)
          )
        .addMappings(
            mapper -> mapper.using(stringToShowTimeTypeConverter).map(DiscountDto::getShowTimeType, Discount::setShowTimeType)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(DiscountDto::getStartTime, Discount::setStartTime)
          )
        .addMappings(
            mapper -> mapper.using(ModelConverter.STRING_TO_LOCALDATETIME_CONVERTER).map(DiscountDto::getEndTime, Discount::setEndTime)
          );
    }
}
