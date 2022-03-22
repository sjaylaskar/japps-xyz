/*
* Id: BookingRequestValidator.java 22-Mar-2022 5:01:53 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.api.internal.contract;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.xyz.apps.ticketeer.booking.resources.Messages;
import com.xyz.apps.ticketeer.booking.service.BookingServiceException;

/**
 * The booking request validator.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class BookingRequestValidator {

    /**
     * Instantiates a new booking request validator.
     */
    private BookingRequestValidator() {

    }

    /**
     * Validate.
     *
     * @param bookingConfirmationRequestDto the booking confirmation request dto
     */
    public static void validate(final BookingConfirmationRequestDto bookingConfirmationRequestDto) {
        if (bookingConfirmationRequestDto == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_BOOKING_CONFIRMATION_REQUEST);
        }

        if (bookingConfirmationRequestDto.getBookingId() == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_ID);
        }

        if (StringUtils.isBlank(bookingConfirmationRequestDto.getBookingReservationId())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_BOOKING_RESERVATION_ID);
        }

        if (bookingConfirmationRequestDto.getEventShowId() == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_EVENT_SHOW_ID);
        }

        if (CollectionUtils.isEmpty(bookingConfirmationRequestDto.getSeatNumbers())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_SEAT_NUMBERS);
        }

        if (StringUtils.isBlank(bookingConfirmationRequestDto.getUsername())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_USERNAME);
        }

        if (StringUtils.isBlank(bookingConfirmationRequestDto.getPassword())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_PASSWORD);
        }
    }

    /**
     * Validate.
     *
     * @param bookingReservationRequestDto the booking reservation request dto
     */
    public static void validate(final BookingReservationRequestDto bookingReservationRequestDto) {
        if (bookingReservationRequestDto == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_BOOKING_RESERVATION_REQUEST);
        }

        if (bookingReservationRequestDto.getEventShowId() == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_EVENT_SHOW_ID);
        }

        if (CollectionUtils.isEmpty(bookingReservationRequestDto.getSeatNumbers())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_SEAT_NUMBERS);
        }

        if (StringUtils.isBlank(bookingReservationRequestDto.getUsername())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_USERNAME);
        }

        if (StringUtils.isBlank(bookingReservationRequestDto.getPassword())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_PASSWORD);
        }

        if (StringUtils.isBlank(bookingReservationRequestDto.getEmailId())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_EMAIL_ID);
        }

        if (StringUtils.isBlank(bookingReservationRequestDto.getPhoneNumber())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_PHONE_NUMBER);
        }
    }

    /**
     * Validate.
     *
     * @param bookingCancellationRequestDto the booking cancellation request dto
     */
    public static void validate(final BookingCancellationRequestDto bookingCancellationRequestDto) {
        if (bookingCancellationRequestDto == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_BOOKING_CANCELLATION_REQUEST);
        }

        if (bookingCancellationRequestDto.getBookingId() == null) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_ID);
        }

        if (StringUtils.isBlank(bookingCancellationRequestDto.getUsername())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_USERNAME);
        }

        if (StringUtils.isBlank(bookingCancellationRequestDto.getPassword())) {
            throw new BookingServiceException(Messages.MESSAGE_ERROR_REQUIRED_PASSWORD);
        }
    }
}
