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

import com.xyz.apps.ticketeer.booking.api.external.ExternalApiUrls;
import com.xyz.apps.ticketeer.booking.api.external.contract.BasicUserDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailsDto;
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
import com.xyz.apps.ticketeer.util.MessageUtil;


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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEATS_RESERVE),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsReservationRequestDto>(eventShowSeatsReservationRequestDto),
                EventShowSeatsReservationResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEATS_BOOK),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingRequestDto>(eventShowSeatsBookingRequestDto),
                EventShowSeatsBookingResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEATS_CANCEL),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsCancellationRequestDto>(eventShowSeatsCancellationRequestDto),
                EventShowSeatsCancellationResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_DETAILED_INFO, eventShowId),
                EventShowDetailedInfoDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEAT_PRICES),
                eventShowSeatPricesRequestDto,
                EventShowSeatPricesResponseDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.PRICING_CALCULATE), bookingPriceInfoDto,
                Double.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode()).withHttpStatus(exception.getStatusCode());
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
     */
    public EventShowDetailsDto findEventShow(@NotNull(message = "The event show id cannot be null") final Long eventShowId) {

        ResponseEntity<EventShowDetailsDto> eventShowDetailsDtoResponseEntity = null;
        try {
            eventShowDetailsDtoResponseEntity = restTemplate().getForEntity(
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.GET_EVENT_SHOW_BY_ID,
                    eventShowId),
                EventShowDetailsDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDetailsDtoResponseEntity)) {
            throw BookingServiceException.nonLocalizedServiceException("Invalid event show id: " + eventShowId);
        }
        return eventShowDetailsDtoResponseEntity.getBody();
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEATS_BY_EVENT_SHOW_ID, eventShowId),
                EventShowSeatForShowResponseDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                UriComponentsBuilder.fromPath(MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.EVENT_SHOW_SEATS_BY_RESERVATION_ID))
                .queryParam("eventShowId", eventShowId)
                .queryParam("bookingReservationId", bookingReservationId)
                .encode()
                .toUriString(),
                EventShowSeatInformationResponseDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.GET_CITY_BY_ID, cityId), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException(exception.getResponseBodyAsString()).withHttpStatus(exception.getStatusCode());
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
                MessageUtil.fromMessageSource(messageSource(), ExternalApiUrls.AUTHENTICATE_USER), basicUserDto,
                Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw BookingServiceException.nonLocalizedServiceException("User is not authenticated.");
        }
    }
}
