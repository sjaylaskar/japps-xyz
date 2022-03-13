/*
* Id: DiscountCreationDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The discount creation dto list.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountCreationDtoList extends DtoList<DiscountCreationDto> {

    /**
     * Instantiates a new discount creation dto list.
     */
    DiscountCreationDtoList() {

    }

    /**
     * Instantiates a new discount creation dto list.
     *
     * @param discountCreationDtos the discount creation dtos
     */
    DiscountCreationDtoList(final List<DiscountCreationDto> discountCreationDtos) {
        super(discountCreationDtos);
    }

    /**
     * Of.
     *
     * @param discountCreationDtos the discount creation dtos
     * @return the discount creation dto list
     */
    public static DiscountCreationDtoList of(final List<DiscountCreationDto> discountCreationDtos) {
        if (CollectionUtils.isEmpty(discountCreationDtos)) {
            return DiscountCreationDtoList.of();
        }
        return new DiscountCreationDtoList(discountCreationDtos);
    }

    /**
     * Of.
     *
     * @return the discount creation dto list
     */
    static DiscountCreationDtoList of() {
        return new DiscountCreationDtoList();
    }
}
