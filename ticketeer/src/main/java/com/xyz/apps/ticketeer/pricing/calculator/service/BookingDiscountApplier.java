/*
 * Id: BookingDiscountApplier.java 04-Mar-2022 12:04:39 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.pricing.calculator.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.xyz.apps.ticketeer.pricing.calculator.discount.model.Discount;
import com.xyz.apps.ticketeer.pricing.calculator.discount.model.DiscountType;
import com.xyz.apps.ticketeer.pricing.calculator.model.BookingPriceInfo;
import com.xyz.apps.ticketeer.pricing.calculator.resources.BookingDiscountApplierMessages;


/**
 * The booking discount applier.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
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

        int nthSeatIndex = discount.getNthSeat() - 1;

        final Set<Integer> nthSeatIndices = new HashSet<>();

        for (final int index = nthSeatIndex; nthSeatIndex < bookingPriceInfo.getSeatBasePrices().size(); nthSeatIndex += discount
            .getNthSeat()) {
            nthSeatIndices.add(index);
        }

        double discountedSeatAmount = 0;
        for (int index = 0; index < bookingPriceInfo.getSeatBasePrices().size(); index++) {
            discountedSeatAmount += (nthSeatIndices.contains(index))
                ? (DiscountType.PERCENTAGE.equals(discount.getDiscountType()))
                    ? bookingPriceInfo.getSeatBasePrices().get(index) * (1 - discount.getValue() / 100)
                    : bookingPriceInfo.getSeatBasePrices().get(index) - discount.getValue()
                : bookingPriceInfo.getSeatBasePrices().get(index);
        }

        bookingPriceInfo.setFinalAmount(discountedSeatAmount);
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

        validateSeatPrices(bookingPriceInfo);
        calculateBaseAmount(bookingPriceInfo);

        validateBookingBaseAmount();
        validateDate();
        validateMinAmount();
        validateMinSeats();
        validateCity();
        validateEventVenue();
        validateNthSeat();
        validateShowTime();
    }

    /**
     * Validate booking base amount.
     */
    private void validateBookingBaseAmount() {

        if (bookingPriceInfo.getBaseAmount() == null || bookingPriceInfo.getBaseAmount() < 0) {
            throw new BookingDiscountApplierException(
                BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_NULL_OR_NEGATIVE_BASE_AMOUNT);
        }
    }

    /**
     * Validate date.
     */
    private void validateDate() {

        if ((discount.getEndTime() != null
            && (bookingPriceInfo.getBookingTime() == null || bookingPriceInfo.getBookingTime().isAfter(discount.getEndTime())))
            || (discount.getStartTime() != null
                && (bookingPriceInfo.getBookingTime() == null
                    || bookingPriceInfo.getBookingTime().isBefore(discount.getStartTime())))) {
            throw new BookingDiscountApplierException(
                BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_APPLICABLE_OFFER_CODE_AT_TIME);
        }
    }

    /**
     * Validate min amount.
     */
    private void validateMinAmount() {

        if (discount.getMinAmount() != null
            && (bookingPriceInfo.getBaseAmount() != null && bookingPriceInfo.getBaseAmount() < discount.getMinAmount())) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_MIN_AMOUNT_FOR_OFFER_CODE,
                discount.getMinAmount());
        }
    }

    /**
     * Validate min seats.
     */
    private void validateMinSeats() {

        if (discount.getMinSeats() != null
            && (bookingPriceInfo.getSeatBasePrices().size() < discount.getMinSeats())) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_MIN_SEATS_FOR_OFFER_CODE,
                discount.getMinSeats());
        }
    }

    /**
     * Validate city.
     */
    private void validateCity() {

        if (CollectionUtils.isNotEmpty(discount.getApplicableCityIds())
            && (bookingPriceInfo.getCityId() == null || !discount.getApplicableCityIds().contains(bookingPriceInfo.getCityId()))) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_APPLICABLE_FOR_CITY);
        }
    }

    /**
     * Validate event venue.
     */
    private void validateEventVenue() {

        if (CollectionUtils.isNotEmpty(discount.getApplicableEventVenueIds())
            && (bookingPriceInfo.getEventVenueId() == null
                || !discount.getApplicableEventVenueIds().contains(bookingPriceInfo.getEventVenueId()))) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_APPLICABLE_FOR_EVENT_VENUE);
        }
    }

    /**
     * Validate nth seat.
     */
    private void validateNthSeat() {

        if (discount.getNthSeat() != null
            && (bookingPriceInfo.getSeatBasePrices().size() < discount.getNthSeat())) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_MIN_SEATS_FOR_OFFER_CODE,
                discount.getNthSeat());
        }
    }

    /**
     * Validate show time.
     */
    private void validateShowTime() {

        if (discount.getShowTimeType() != null
            && (bookingPriceInfo.getShowStartTime() == null
                || !discount.getShowTimeType().isInShowTimeRange(bookingPriceInfo.getShowStartTime()))) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_APPLICABLE_FOR_SHOW_TIME,
                discount.getShowTimeType().name().toLowerCase(), discount.getShowTimeType().rangeString());
        }
    }

    /**
     * Validate seat prices.
     *
     * @param bookingPriceInfo the booking price info
     */
    public static void validateSeatPrices(final BookingPriceInfo bookingPriceInfo) {

        if (CollectionUtils.isEmpty(bookingPriceInfo.getSeatBasePrices())) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_EMPTY_SEAT_PRICES);
        }

        if (bookingPriceInfo.getSeatBasePrices()
            .stream()
            .filter(Objects::nonNull)
            .anyMatch(seatPrice -> seatPrice < 0)) {
            throw new BookingDiscountApplierException(BookingDiscountApplierMessages.MESSAGE_ERROR_NOT_NULL_OR_NEGATIVE_SEAT_PRICE);
        }
    }

    /**
     * Calculate base amount.
     *
     * @param bookingPriceInfo the booking price info
     * @return the double
     */
    public static void calculateBaseAmount(final BookingPriceInfo bookingPriceInfo) {

        bookingPriceInfo.setBaseAmount(bookingPriceInfo.getSeatBasePrices().stream().mapToDouble(Double::valueOf).sum());
        bookingPriceInfo.setFinalAmount(bookingPriceInfo.getBaseAmount());
    }
}
