/*
* Id: AuditoriumSeatModelMapper.java 14-Feb-2022 10:56:26 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.eventvenue.service.modelmapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDetailsDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatRowDto;
import com.xyz.apps.ticketeer.eventvenue.api.internal.contract.AuditoriumSeatsDto;
import com.xyz.apps.ticketeer.eventvenue.model.Auditorium;
import com.xyz.apps.ticketeer.eventvenue.model.AuditoriumSeat;
import com.xyz.apps.ticketeer.general.model.GeneralModelMapper;

/**
 * The auditorium seat model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@Component
public class AuditoriumSeatModelMapper extends GeneralModelMapper<AuditoriumSeat, AuditoriumSeatDetailsDto> {

    private static final String SEAT_NUMBER_PREFIX = "S";

    /**
     * Instantiates a new auditorium seat model mapper.
     */
    public AuditoriumSeatModelMapper() {

        super(AuditoriumSeat.class, AuditoriumSeatDetailsDto.class);

    }

    /**
     * Initializes the mappings.
     */
    @PostConstruct
    private void initMappings() {

        final TypeMap<AuditoriumSeat, AuditoriumSeatDetailsDto> auditoriumSeatToAuditoriumSeatDetailsDtoMap = modelMapper.createTypeMap(AuditoriumSeat.class, AuditoriumSeatDetailsDto.class);
        auditoriumSeatToAuditoriumSeatDetailsDtoMap
        .addMappings(
          mapper -> mapper.map(auditoriumSeat -> auditoriumSeat.getAuditorium().getId(), AuditoriumSeatDetailsDto::setAuditoriumId)
        );

        final Converter<Long, Auditorium> auditoriumIdToAuditoriumConverter = converter -> new Auditorium().id(converter.getSource());
        final TypeMap<AuditoriumSeatDetailsDto, AuditoriumSeat> auditoriumSeatDetailsDtoToAuditoriumSeatMap = modelMapper.createTypeMap(AuditoriumSeatDetailsDto.class, AuditoriumSeat.class);
        auditoriumSeatDetailsDtoToAuditoriumSeatMap
        .addMappings(
          mapper -> mapper.using(auditoriumIdToAuditoriumConverter).map(AuditoriumSeatDetailsDto::getAuditoriumId, AuditoriumSeat::setAuditorium)
        );
    }

    /**
     * To auditorium seat.
     *
     * @param auditorium the auditorium
     * @param rowName the row name
     * @param seatNumber the seat number
     * @return the auditorium seat
     */
    public AuditoriumSeat toAuditoriumSeat(final Auditorium auditorium, final String rowName, final Integer seatNumber) {
        if (auditorium != null && StringUtils.isNotBlank(rowName) && seatNumber != null && seatNumber >= 1) {
            final AuditoriumSeat auditoriumSeat = new AuditoriumSeat();
            auditoriumSeat.setAuditorium(auditorium);
            auditoriumSeat.setRowName(rowName);
            auditoriumSeat.setSeatNumber(seatNumber);
            return auditoriumSeat;
        }
        return null;
    }

    /**
     * To auditorium seats dto.
     *
     * @param eventVenueId the event venue id
     * @param auditoriumName the auditorium name
     * @param auditoriumSeats the auditorium seats
     * @return the auditorium seats dto
     */
    public AuditoriumSeatsDto toAuditoriumSeatsDto(final Long eventVenueId, final String auditoriumName, final List<AuditoriumSeat> auditoriumSeats) {
        if (eventVenueId != null && StringUtils.isNotBlank(auditoriumName) && CollectionUtils.isNotEmpty(auditoriumSeats)) {
            final AuditoriumSeatsDto auditoriumSeatsDto = new AuditoriumSeatsDto();
            auditoriumSeatsDto.setEventVenueId(eventVenueId);
            auditoriumSeatsDto.setAuditoriumName(auditoriumName);
            auditoriumSeatsDto.setAuditoriumSeatRows(
            auditoriumSeats.stream().collect(Collectors.groupingBy(AuditoriumSeat::getRowName))
            .entrySet().stream()
            .map(rowToAuditoriumSeatsEntry
                -> AuditoriumSeatRowDto.of(rowToAuditoriumSeatsEntry.getKey(),
                    rowToAuditoriumSeatsEntry.getValue()
                    .stream().map(auditoriumSeat -> AuditoriumSeatDto.of(auditoriumSeat.getId(), SEAT_NUMBER_PREFIX + auditoriumSeat.getSeatNumber())).toList()))
            .toList());

            return auditoriumSeatsDto;
        }
        return null;
    }
}
