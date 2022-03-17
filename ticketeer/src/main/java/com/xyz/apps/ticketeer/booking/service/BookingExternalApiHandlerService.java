/*
 * Id: BookingExternalApiHandlerService.java 09-Mar-2022 7:48:04 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.booking.service;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import com.xyz.apps.ticketeer.booking.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.booking.api.external.contract.BasicUserDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatForShowResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatInformationResponseDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatPricesResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsCancellationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsCancellationResponseDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationRequestDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsReservationResponseDto;
import com.xyz.apps.ticketeer.general.service.GeneralService;
import com.xyz.apps.ticketeer.general.service.ServiceUtil;
import com.xyz.apps.ticketeer.util.StringUtil;


/**
 * The booking external api handler service.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Service
@Validated
public class BookingExternalApiHandlerService extends GeneralService {

    /**
     * Reserve.
     *
     * @param eventShowSeatsReservationRequestDto the event show seats reservation request dto
     * @return the event show seats reservation response dto
     */
    public EventShowSeatsReservationResponseDto reserve(
            @NotNull(message = "The event show seats reservation request cannot be null.")
            final EventShowSeatsReservationRequestDto eventShowSeatsReservationRequestDto) {

        ResponseEntity<EventShowSeatsReservationResponseDto> eventShowSeatsReservationResponseDtoResponseEntity = null;
        try {
            eventShowSeatsReservationResponseDtoResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsReservationRequestDto>(eventShowSeatsReservationRequestDto),
                EventShowSeatsReservationResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatsReservationResponseDtoResponseEntity)
            || StringUtils.isBlank(eventShowSeatsReservationResponseDtoResponseEntity.getBody().getBookingReservationId())
            || BooleanUtils.isFalse(eventShowSeatsReservationResponseDtoResponseEntity.getBody().getAreSeatsReserved())) {
            throw new SelectedSeatsUnavailableException();
        }

        return eventShowSeatsReservationResponseDtoResponseEntity.getBody();
    }

    /**
     * Book.
     *
     * @param eventShowSeatsBookingRequestDto the event show seats booking request dto
     * @return the event show seats booking response dto
     */
    public EventShowSeatsBookingResponseDto book(
            @NotNull(message = "The event show seats reservation request cannot be null.")
            final EventShowSeatsBookingRequestDto eventShowSeatsBookingRequestDto) {

        ResponseEntity<EventShowSeatsBookingResponseDto> eventShowSeatsBookingResponseDtoResponseEntity = null;
        try {
            eventShowSeatsBookingResponseDtoResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BOOK.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingRequestDto>(eventShowSeatsBookingRequestDto),
                EventShowSeatsBookingResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatsBookingResponseDtoResponseEntity)
                || StringUtils.isBlank(eventShowSeatsBookingResponseDtoResponseEntity.getBody().getBookingReservationId())
                || BooleanUtils.isFalse(eventShowSeatsBookingResponseDtoResponseEntity.getBody().getAreSeatsBooked())) {
                throw new SelectedSeatsUnavailableException();
            }

            return eventShowSeatsBookingResponseDtoResponseEntity.getBody();
    }

    /**
     * Cancel booking for event show seats.
     *
     * @param eventShowSeatsCancellationRequestDto the event show seats cancellation request dto
     */
    public void cancel(
            @NotNull(
                message = "The event show seats cancellation request cannot be null."
            ) final EventShowSeatsCancellationRequestDto eventShowSeatsCancellationRequestDto) {

        ResponseEntity<EventShowSeatsCancellationResponseDto> eventShowSeatsCancellationResponseEntity = null;
        try {

            eventShowSeatsCancellationResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_CANCEL.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsCancellationRequestDto>(eventShowSeatsCancellationRequestDto),
                EventShowSeatsCancellationResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatsCancellationResponseEntity)
            || BooleanUtils.isFalse(eventShowSeatsCancellationResponseEntity.getBody().getAreSeatsCancelled())) {
            throw BookingServiceException.nonLocalizedServiceException("Failed to cancel the seat reservation.");
        }
    }

    /**
     * Finds the event show details by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show detailed info dto
     */
    public EventShowDetailedInfoDto findEventShowDetailsByEventShowId(@NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        ResponseEntity<EventShowDetailedInfoDto> eventShowDetailedInfoDtoResponseEntity = null;
        try {
            eventShowDetailedInfoDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_DETAILED_INFO.get()), eventShowId),
                EventShowDetailedInfoDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDetailedInfoDtoResponseEntity)) {
            throw BookingServiceException.nonLocalizedServiceException("Could not find details for event show id: " + eventShowId);
        }
        return eventShowDetailedInfoDtoResponseEntity.getBody();
    }

    /**
     * Obtain event show seat prices.
     *
     * @param eventShowSeatPricesRequestDto the event show seat prices request dto
     * @return the event show seat prices response dto
     */
    public EventShowSeatPricesResponseDto obtainEventShowSeatPrices(
            @NotNull(message = "The event show seat prices request cannot be null.")
            final EventShowSeatPricesRequestDto eventShowSeatPricesRequestDto) {

        ResponseEntity<EventShowSeatPricesResponseDto> eventShowSeatPricesResponseDtoResponseEntity = null;
        try {
            eventShowSeatPricesResponseDtoResponseEntity
            = restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEAT_PRICES.get()),
                eventShowSeatPricesRequestDto,
                EventShowSeatPricesResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatPricesResponseDtoResponseEntity)
            || CollectionUtils.isEmpty(eventShowSeatPricesResponseDtoResponseEntity.getBody().getEventShowSeatNumberPrices())) {
            throw BookingServiceException.nonLocalizedServiceException("Failed to obtain seat prices for selected seats.");
        }

        return eventShowSeatPricesResponseDtoResponseEntity.getBody();
    }

    /**
     * Calculate final booking amount.
     *
     * @param bookingPriceInfoDto the booking price info dto
     * @return the amount
     */
    public Double calculateFinalBookingAmount(final BookingPriceInfoDto bookingPriceInfoDto) {

        ResponseEntity<Double> bookingFinalAmountResponseEntity = null;
        try {
            bookingFinalAmountResponseEntity = restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.PRICING_CALCULATE.get()), bookingPriceInfoDto,
                Double.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(bookingFinalAmountResponseEntity)) {
            throw BookingServiceException.nonLocalizedServiceException("Failed to calculate final amount for booking.");
        }

        return bookingFinalAmountResponseEntity.getBody();
    }

    /**
     * Finds the event show.
     *
     * @param eventShowId the event show id
     * @return the event show dto
     */
    public EventShowDto findEventShow(@NotNull(message = "The event show id cannot be null") final Long eventShowId) {

        ResponseEntity<EventShowDto> eventShowDtoResponseEntity = null;
        try {
            eventShowDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.GET_EVENT_SHOW_BY_ID.get()),
                    eventShowId),
                EventShowDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDtoResponseEntity)) {
            throw BookingServiceException.nonLocalizedServiceException("Invalid event show id: " + eventShowId);
        }
        return eventShowDtoResponseEntity.getBody();
    }

    /**
     * Finds the event show seats for show.
     *
     * @param eventShowId the event show id
     * @return the event show seat for show response dto list
     */
    public EventShowSeatForShowResponseDtoList findEventShowSeatsForShow(@NotNull(
        message = "The event show id cannot be null."
    ) final Long eventShowId) {

        ResponseEntity<EventShowSeatForShowResponseDtoList> eventShowSeatForShowResponseDtoListResponseEntity = null;
        try {
            eventShowSeatForShowResponseDtoListResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BY_EVENT_SHOW_ID.get()), eventShowId),
                EventShowSeatForShowResponseDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatForShowResponseDtoListResponseEntity)
            || CollectionUtils.isEmpty(eventShowSeatForShowResponseDtoListResponseEntity.getBody().getDtos())) {
            throw BookingServiceException.nonLocalizedServiceException("Could not find seats for event show id: " + eventShowId);
        }

        return eventShowSeatForShowResponseDtoListResponseEntity.getBody();
    }

    /**
     * Finds the event show seats by booking reservation.
     *
     * @param eventShowId the event show id
     * @param bookingReservationId the booking reservation id
     * @return the event show seat information response dto list
     */
    public EventShowSeatInformationResponseDtoList findEventShowSeatsByBookingReservation(
            @NotNull(message = "The event show id cannot be null.") final Long eventShowId,
            @NotNull(message = "The booking reservation id cannot be null") final String bookingReservationId) {

        ResponseEntity<EventShowSeatInformationResponseDtoList> eventShowInfoListResponseEntity = null;
        try {
            eventShowInfoListResponseEntity = restTemplate().getForEntity(
                UriComponentsBuilder.fromPath(StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BY_RESERVATION_ID.get())))
                .queryParam("eventShowId", eventShowId)
                .queryParam("bookingReservationId", bookingReservationId)
                .encode()
                .toUriString(),
                EventShowSeatInformationResponseDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowInfoListResponseEntity)
            || CollectionUtils.isEmpty(eventShowInfoListResponseEntity.getBody().getDtos())) {
            throw BookingServiceException.nonLocalizedServiceException("Could not find seats for event show id: " + eventShowId + " and booking reservation id: " + bookingReservationId);
        }

        return eventShowInfoListResponseEntity.getBody();
    }

    /**
     * Finds the city.
     *
     * @param cityId the city id
     * @return the city dto
     */
    public CityDto findCity(@NotNull(message = "The city id cannot be null") final Long cityId) {

        ResponseEntity<CityDto> cityDtoResponseEntity = null;
        try {
            cityDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), cityId), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString());
        }
        if (ServiceUtil.notHasBodyResponseEntity(cityDtoResponseEntity)) {
            throw BookingServiceException.nonLocalizedServiceException("Invalid city id: " + cityId);
        }
        return cityDtoResponseEntity.getBody();
    }

    /**
     * Validate user.
     *
     * @param username the username
     * @param password the password
     */
    public void validateUser(final String username, final String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw BookingServiceException.nonLocalizedServiceException("User is not authenticated.");
        }

        final BasicUserDto basicUserDto = new BasicUserDto();
        basicUserDto.setUsername(username);
        basicUserDto.setPassword(password);

        try {
            restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.AUTHENTICATE_USER.get()), basicUserDto,
                Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException("User is not authenticated.");
        }
    }
}
