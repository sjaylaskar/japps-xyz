/*
 * Id: EventShowSeatModificationModelMapper.java 03-Mar-2022 6:06:14 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.eventvenue.eventshow.seat.model;

import javax.annotation.PostConstruct;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.model.EventShow;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatModificationResponseDto;
import com.xyz.apps.ticketeer.eventvenue.eventshow.seat.api.internal.contract.EventShowSeatRowPriceDto;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;
import com.xyz.apps.ticketeer.general.model.ModelConverter;


/**
 * The event show seat modification model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class EventShowSeatModificationModelMapper extends GeneralModelMapper<EventShowSeat, EventShowSeatModificationResponseDto> {

    /**
     * Instantiates a new event show seat model mapper.
     */
    public EventShowSeatModificationModelMapper() {

        super(EventShowSeat.class, EventShowSeatModificationResponseDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<EventShowSeat, EventShowSeatModificationResponseDto> eventShowSeatToEventShowSeatDtoMap = modelMapper
            .createTypeMap(EventShowSeat.class, EventShowSeatModificationResponseDto.class);
        eventShowSeatToEventShowSeatDtoMap
            .addMappings(
                mapper -> mapper.using(ModelConverter.ENUM_TO_NAME_CONVERTER).map(EventShowSeat::getSeatReservationStatus,
                    EventShowSeatModificationResponseDto::setSeatReservationStatus))
            .addMappings(
                mapper -> mapper.map(eventShowSeat -> eventShowSeat.getEventShow().getId(),
                    EventShowSeatModificationResponseDto::setEventShowId));
    }

    /**
     * To event show seat.
     *
     * @param eventShow the event show
     * @param eventShowSeatRowPrice the event show seat row price
     * @param auditoriumSeatDto the auditorium seat dto
     * @return the event show seat
     */
    public EventShowSeat toEventShowSeat(final EventShow eventShow, final EventShowSeatRowPriceDto eventShowSeatRowPrice,
            final AuditoriumSeatDto auditoriumSeatDto) {

        final EventShowSeat eventShowSeat = new EventShowSeat();
        eventShowSeat.setEventShow(eventShow);
        eventShowSeat.setRowName(eventShowSeatRowPrice.getAuditoriumSeatRowName());
        eventShowSeat.setAmount(eventShowSeatRowPrice.getAmount());
        eventShowSeat.setSeatNumber(constructSeatNumber(eventShowSeatRowPrice, auditoriumSeatDto));
        return eventShowSeat;
    }

    /**
     * Construct seat number.
     *
     * @param eventShowSeatRowPrice the event show seat row price
     * @param auditoriumSeatDto the auditorium seat dto
     * @return the string
     */
    private String constructSeatNumber(final EventShowSeatRowPriceDto eventShowSeatRowPrice,
            final AuditoriumSeatDto auditoriumSeatDto) {

        return eventShowSeatRowPrice.getAuditoriumSeatRowName() + auditoriumSeatDto.getSeatNumber();
    }

}
