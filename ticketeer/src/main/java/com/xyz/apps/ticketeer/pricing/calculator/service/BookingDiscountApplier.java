/*
 * Id: BookingDiscountApplier.java 04-Mar-2022 12:04:39 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountType;
import com.xyz.apps.ticketeer.pricing.calculator.discount.service.DiscountOfferCodeAlreadyExistsException;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The booking discount applier.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class BookingDiscountApplier {

    /** The booking price info. */
    private BookingPriceInfo bookingPriceInfo;

    /** The discount. */
    private Discount discount;

    /**
     * Instantiates a new booking discount applier.
     *
     * @param bookingPriceInfo the booking price info
     * @param discount the discount
     */
    public BookingDiscountApplier(final BookingPriceInfo bookingPriceInfo, final Discount discount) {

        this.bookingPriceInfo = bookingPriceInfo;
        this.discount = discount;
    }

    /**
     * Apply flat discount.
     */
    public void applyFlatDiscount() {

        applyStraightDiscount();
    }

    /**
     * Apply nth seat discount.
     */
    public void applyNthSeatDiscount() {

        validate();

        final double pricePerSeat = bookingPriceInfo.getBaseAmount() / bookingPriceInfo.getNumberOfSeats();

        final int nthSeatCount = bookingPriceInfo.getNumberOfSeats() / discount.getNthSeat();
        final int remSeatCount = bookingPriceInfo.getNumberOfSeats() - nthSeatCount;

        bookingPriceInfo.setFinalAmount(pricePerSeat * remSeatCount
            + ((DiscountType.PERCENTAGE.equals(discount.getDiscountType()))
                ? nthSeatCount * (pricePerSeat * (1 - discount.getValue() / 100))
                : nthSeatCount * (pricePerSeat - discount.getValue())));
    }

    /**
     * Apply N seats discount.
     */
    public void applyNSeatsDiscount() {

        applyStraightDiscount();
    }

    /**
     * Apply.
     */
    private void applyStraightDiscount() {

        validate();

        bookingPriceInfo.setFinalAmount(DiscountType.PERCENTAGE.equals(discount.getDiscountType())
            ? bookingPriceInfo.getBaseAmount() * (1 - discount.getValue() / 100)
            : bookingPriceInfo.getBaseAmount() - discount.getValue());
    }

    /**
     * Apply show time discount.
     */
    public void applyShowTimeDiscount() {

        applyStraightDiscount();
    }

    /**
     * Validate.
     */
    private void validate() {

        validateDate();
        validateMinAmount();
        validateMinSeats();
        validateCity();
        validateEventVenue();
        validateNthSeat();
        validateShowTime();
    }

    /**
     * Validate date.
     */
    private void validateDate() {
        if ((discount.getEndTime() != null && (bookingPriceInfo.getBookingTime() == null || bookingPriceInfo.getBookingTime().isAfter(discount.getEndTime())))
            || (discount.getStartTime() != null && (bookingPriceInfo.getBookingTime() == null || bookingPriceInfo.getBookingTime().isBefore(discount.getStartTime())))) {
            throw new DiscountOfferCodeAlreadyExistsException("The offer code is not applicable at this time.");
        }
    }

    /**
     * Validate min amount.
     */
    private void validateMinAmount() {

        if (discount.getMinAmount() != null
            && (bookingPriceInfo.getBaseAmount() != null && bookingPriceInfo.getBaseAmount() < discount.getMinAmount())) {
            throw new DiscountOfferCodeAlreadyExistsException("Minimum amount required for this offer code is: " + discount.getMinAmount());
        }
    }

    /**
     * Validate min seats.
     */
    private void validateMinSeats() {

        if (discount.getMinSeats() != null
            && (bookingPriceInfo.getNumberOfSeats() == null || bookingPriceInfo.getNumberOfSeats() < discount.getMinSeats())) {
            throw new DiscountOfferCodeAlreadyExistsException("Minimum number of seats to be booked for this offer code is: "
                + discount.getMinSeats());
        }
    }

    /**
     * Validate city.
     */
    private void validateCity() {

        if (CollectionUtils.isNotEmpty(discount.getApplicableCityIds())
            && (bookingPriceInfo.getCityId() == null || !discount.getApplicableCityIds().contains(bookingPriceInfo.getCityId()))) {
            throw new DiscountOfferCodeAlreadyExistsException("The offer is not applicable for this city.");
        }
    }

    /**
     * Validate event venue.
     */
    private void validateEventVenue() {

        if (CollectionUtils.isNotEmpty(discount.getApplicableEventVenueIds())
            && (bookingPriceInfo.getEventVenueId() == null
                || !discount.getApplicableEventVenueIds().contains(bookingPriceInfo.getEventVenueId()))) {
            throw new DiscountOfferCodeAlreadyExistsException("The offer is not applicable for this venue.");
        }
    }

    /**
     * Validate nth seat.
     */
    private void validateNthSeat() {

        if (discount.getNthSeat() != null
            && (bookingPriceInfo.getNumberOfSeats() == null || bookingPriceInfo.getNumberOfSeats() < discount.getNthSeat())) {
            throw new DiscountOfferCodeAlreadyExistsException("Minimum number of seats to be booked for this offer code is: "
                + discount.getNthSeat());
        }
    }

    /**
     * Validate show time.
     */
    private void validateShowTime() {

        if (discount.getShowTimeType() != null
            && (bookingPriceInfo.getShowStartTime() == null
                || !discount.getShowTimeType().isInShowTimeRange(bookingPriceInfo.getShowStartTime()))) {
            throw new DiscountOfferCodeAlreadyExistsException("The offer is only applicable for "
                + discount.getShowTimeType().name().toLowerCase() + " shows between " + discount.getShowTimeType().rangeString());
        }
    }
}
