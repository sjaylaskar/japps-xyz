/*
* Id: BookingExternalApiHandlerService.java 09-Mar-2022 7:48:04 pm SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.booking.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;

import com.xyz.apps.ticketeer.booking.api.external.ApiPropertyKey;
import com.xyz.apps.ticketeer.booking.api.external.contract.BasicUserDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.BookingPriceInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.CityDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDetailedInfoDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatDtoList;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatNumbersDto;
import com.xyz.apps.ticketeer.booking.api.external.contract.EventShowSeatsBookingDto;
import com.xyz.apps.ticketeer.booking.api.internal.contract.BookingDto;
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
     * Finds the event show details by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show detailed info dto
     */
    public EventShowDetailedInfoDto findEventShowDetailsByEventShowId(@NotNull(message = "The event show id cannot be null.") final Long eventShowId) {
        ResponseEntity<EventShowDetailedInfoDto> eventShowDetailedInfoDtoResponseEntity = null;
        try {
            eventShowDetailedInfoDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_DETAILED_INFO.get()), eventShowId),
                EventShowDetailedInfoDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDetailedInfoDtoResponseEntity)) {
            throw new BookingServiceException("Could not find details for event show id: " + eventShowId);
        }
        return eventShowDetailedInfoDtoResponseEntity.getBody();
    }

    /**
     * Finds the seat numbers.
     *
     * @param eventShowSeatIds the event show seat ids
     * @return the seat numbers
     */
    public List<String> findSeatNumbers(@NotEmpty(
        message = "The event show seat ids cannot be empty."
    ) final Set<Long> eventShowSeatIds) {

        ResponseEntity<EventShowSeatNumbersDto> seatNumbersResponseEntity = null;
        try {
            seatNumbersResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEAT_NUMBERS.get()),
                    eventShowSeatIds.stream().map(value -> Long.toString(value)).collect(Collectors.joining(","))),
                EventShowSeatNumbersDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
        if (ServiceUtil.notHasBodyResponseEntity(seatNumbersResponseEntity)
            || CollectionUtils.isEmpty(seatNumbersResponseEntity.getBody().getSeatNumbers())) {
            throw new BookingServiceException("Seats not found for ids: " + eventShowSeatIds);
        }

        return seatNumbersResponseEntity.getBody().getSeatNumbers();
    }

    /**
     * Reserve seats.
     *
     * @param bookingDto the booking dto
     * @return the count of seats reserved.
     */
    public Integer reserveSeats(final BookingDto bookingDto) {

        ResponseEntity<Integer> eventShowSeatsReservedCountResponseEntity = null;
        try {
            eventShowSeatsReservedCountResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE.get()),
                HttpMethod.PUT,
                new HttpEntity<Set<Long>>(bookingDto.getEventShowSeatIds()),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return ServiceUtil.hasBodyResponseEntity(eventShowSeatsReservedCountResponseEntity)
            ? eventShowSeatsReservedCountResponseEntity.getBody() : 0;
    }

    /**
     * Reserve seats with booking.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the count of seats reserved.
     */
    public Integer reserveSeatsWithBooking(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Integer> eventShowSeatsBookingReservedCountResponseEntity = null;
        try {
            eventShowSeatsBookingReservedCountResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_RESERVE_WITH_BOOKING.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingDto>(eventShowSeatsBookingDto),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return ServiceUtil.hasBodyResponseEntity(eventShowSeatsBookingReservedCountResponseEntity)
            ? eventShowSeatsBookingReservedCountResponseEntity.getBody() : 0;
    }

    /**
     * Are event show seats available.
     *
     * @param bookingDto the booking dto
     * @return {@code true}, if event show seats are available.
     */
    public boolean areEventShowSeatsAvailable(final BookingDto bookingDto) {

        ResponseEntity<Boolean> eventShowSeatsAreAvailableResponseEntity = null;
        try {
            eventShowSeatsAreAvailableResponseEntity = restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_AVAILABLE.get()), bookingDto
                    .getEventShowSeatIds(), Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return ServiceUtil.hasBodyResponseEntity(eventShowSeatsAreAvailableResponseEntity)
            ? eventShowSeatsAreAvailableResponseEntity.getBody() : false;
    }

    /**
     * Are event show seats reserved.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return {@code true}, if event show seats are reserved for booking.
     */
    public boolean areEventShowSeatsReserved(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Boolean> eventShowSeatsAreReservedResponseEntity = null;
        try {
            eventShowSeatsAreReservedResponseEntity = restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_ARE_RESERVED.get()),
                eventShowSeatsBookingDto, Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return ServiceUtil.hasBodyResponseEntity(eventShowSeatsAreReservedResponseEntity) ? eventShowSeatsAreReservedResponseEntity
            .getBody() : false;
    }

    /**
     * Cancel booking for event show seats.
     *
     * @param bookingId the booking id
     */
    public void cancelBookingForEventShowSeats(final Long bookingId) {

        try {
            restTemplate().put(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_CANCEL.get()), bookingId);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Unreserve seats.
     *
     * @param bookingDto the booking dto
     */
    public void unreserveSeats(final BookingDto bookingDto) {

        try {
            restTemplate().put(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_UNRESERVE.get()), bookingDto
                    .getEventShowSeatIds());
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
    }

    /**
     * Calculate event show seats amount.
     *
     * @param bookingDto the booking dto
     * @return the amount
     */
    public Double calculateEventShowSeatsAmount(final BookingDto bookingDto) {

        ResponseEntity<Double> eventShowSeatsAmountResponseEntity = null;
        try {
            eventShowSeatsAmountResponseEntity = restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.CALCULATE_EVENT_SHOW_SEATS_AMOUNT.get()), bookingDto
                    .getEventShowSeatIds(), Double.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatsAmountResponseEntity)) {
            throw new BookingServiceException("Failed to calculate seats amount for booking: " + bookingDto);
        }

        return eventShowSeatsAmountResponseEntity.getBody();
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
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(bookingFinalAmountResponseEntity)) {
            throw new BookingServiceException("Failed to calculate final amount for booking.");
        }

        return bookingFinalAmountResponseEntity.getBody();
    }

    /**
     * Book seats.
     *
     * @param eventShowSeatsBookingDto the event show seats booking dto
     * @return the count of booked seats
     */
    public Integer bookSeats(final EventShowSeatsBookingDto eventShowSeatsBookingDto) {

        ResponseEntity<Integer> eventShowSeatsBookedCountResponseEntity = null;
        try {
            eventShowSeatsBookedCountResponseEntity = restTemplate().exchange(
                environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BOOK.get()),
                HttpMethod.PUT,
                new HttpEntity<EventShowSeatsBookingDto>(eventShowSeatsBookingDto),
                Integer.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        return ServiceUtil.hasBodyResponseEntity(eventShowSeatsBookedCountResponseEntity) ? eventShowSeatsBookedCountResponseEntity
            .getBody() : 0;
    }

    /**
     * Finds the city.
     *
     * @param bookingDto the booking dto
     * @return the city dto
     */
    public CityDto findCity(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (bookingDto.getCityId() == null) {
            throw new BookingServiceException("The city id cannot be null.");
        }
        ResponseEntity<CityDto> cityDtoResponseEntity = null;
        try {
            cityDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.GET_CITY_BY_ID.get()), bookingDto
                    .getCityId()), CityDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }
        if (ServiceUtil.notHasBodyResponseEntity(cityDtoResponseEntity)) {
            throw new BookingServiceException("Invalid city id: " + bookingDto.getEventShowId());
        }
        return cityDtoResponseEntity.getBody();
    }

    /**
     * Finds the event show.
     *
     * @param bookingDto the booking dto
     * @return the event show dto
     */
    public EventShowDto findEventShow(@NotNull(message = "The booking cannot be null") final BookingDto bookingDto) {

        if (bookingDto.getEventShowId() == null) {
            throw new BookingServiceException("The event show id cannot be null.");
        }
        ResponseEntity<EventShowDto> eventShowDtoResponseEntity = null;
        try {
            eventShowDtoResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.GET_EVENT_SHOW_BY_ID.get()),
                    bookingDto.getEventShowId()),
                EventShowDto.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowDtoResponseEntity)) {
            throw new BookingServiceException("Invalid event show id: " + bookingDto.getEventShowId());
        }
        return eventShowDtoResponseEntity.getBody();
    }

    /**
     * Validate user.
     *
     * @param username the username
     * @param password the password
     */
    public void validateUser(final String username, final String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BookingServiceException("User is not authenticated.");
        }

        final BasicUserDto basicUserDto = new BasicUserDto();
        basicUserDto.setUsername(username);
        basicUserDto.setPassword(password);

        try {
            restTemplate().postForEntity(
                environment().getProperty(ApiPropertyKey.AUTHENTICATE_USER.get()), basicUserDto,
                Boolean.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException("User is not authenticated.");
        }
    }

    /**
     * Finds the event show seat ids by event show id.
     *
     * @param eventShowId the event show id
     * @return the event show seat minimal dto list
     */
    public Set<Long> findEventShowSeatIdsByEventShowId(@NotNull(message = "The event show id cannot be null.") final Long eventShowId) {
        ResponseEntity<EventShowSeatDtoList> eventShowSeatMinimalDtosResponseEntity = null;
        try {
            eventShowSeatMinimalDtosResponseEntity = restTemplate().getForEntity(
                StringUtil.format(environment().getProperty(ApiPropertyKey.EVENT_SHOW_SEATS_BY_EVENT_SHOW_ID.get()), eventShowId),
                EventShowSeatDtoList.class);
        } catch (final HttpStatusCodeException exception) {
            throw new BookingServiceException(exception.getResponseBodyAsString());
        }

        if (ServiceUtil.notHasBodyResponseEntity(eventShowSeatMinimalDtosResponseEntity)) {
            throw new BookingServiceException("Could not find seats for event show id: " + eventShowId);
        }

        return getSeatIds(eventShowId, eventShowSeatMinimalDtosResponseEntity);
    }

    /**
     * Gets the seat ids.
     *
     * @param eventShowId the event show id
     * @param eventShowSeatMinimalDtosResponseEntity the event show seat minimal dtos response entity
     * @return the seat ids
     */
    private Set<Long> getSeatIds(final Long eventShowId, final ResponseEntity<EventShowSeatDtoList> eventShowSeatMinimalDtosResponseEntity) {
        if (CollectionUtils.isEmpty(eventShowSeatMinimalDtosResponseEntity.getBody().getDtos())) {
            throw new BookingServiceException("Could not find seats for event show id: " + eventShowId);
        }

        return eventShowSeatMinimalDtosResponseEntity.getBody().getDtos().stream().map(EventShowSeatDto::getId).collect(Collectors.toSet());
    }
}
