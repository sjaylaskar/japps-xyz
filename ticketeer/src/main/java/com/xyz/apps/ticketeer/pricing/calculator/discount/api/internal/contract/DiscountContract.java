/*
* Id: DiscountContract.java 14-Mar-2022 1:03:14 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.pricing.calculator.discount.api.internal.contract;

import java.util.Set;

/**
 * The discount contract.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface DiscountContract {

    /**
     * Gets the id.
     *
     * @return the id
     */
    default String getId() {
        return null;
    }

    /**
     * Gets the discount strategy.
     *
     * @return the discount strategy
     */
    String getDiscountStrategy();

    /**
     * Gets the applicable city ids.
     *
     * @return the applicable city ids
     */
    Set<Long> getApplicableCityIds();

    /**
     * Gets the applicable event venue ids.
     *
     * @return the applicable event venue ids
     */
    Set<Long> getApplicableEventVenueIds();

    /**
     * Gets the min amount.
     *
     * @return the min amount
     */
    Double getMinAmount();

    /**
     * Gets the min seats.
     *
     * @return the min seats
     */
    Integer getMinSeats();

    /**
     * Gets the nth seat.
     *
     * @return the nth seat
     */
    Integer getNthSeat();

    /**
     * Gets the show time type.
     *
     * @return the show time type
     */
    String getShowTimeType();

    /**
     * Gets the discount type.
     *
     * @return the discount type
     */
    String getDiscountType();

    /**
     * Gets the value.
     *
     * @return the value
     */
    Double getValue();

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    String getStartTime();

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    String getEndTime();
}
