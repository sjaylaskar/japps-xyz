/*
* Id: CityDtoList.java 14-Feb-2022 11:37:31 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.general.model.DtoList;

import lombok.Getter;
import lombok.Setter;

/**
 * The discount dto list.
 */
@Getter
@Setter
public class DiscountDtoList extends DtoList<DiscountDto> {

    /**
     * Instantiates a new discount dto list.
     */
    DiscountDtoList() {

    }

    /**
     * Instantiates a new discount dto list.
     *
     * @param discountDtos the discount dtos
     */
    DiscountDtoList(final List<DiscountDto> discountDtos) {
        super(discountDtos);
    }

    /**
     * Of.
     *
     * @param discountDtos the discount dtos
     * @return the discount dto list
     */
    static DiscountDtoList of(final List<DiscountDto> discountDtos) {
        if (CollectionUtils.isEmpty(discountDtos)) {
            return DiscountDtoList.of();
        }
        return new DiscountDtoList(discountDtos);
    }

    /**
     * Of.
     *
     * @return the discount dto list
     */
    static DiscountDtoList of() {
        return new DiscountDtoList();
    }
}
