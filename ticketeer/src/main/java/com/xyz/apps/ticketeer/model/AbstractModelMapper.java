/*
 * Id: AbstractModelMapper.java 14-Feb-2022 6:40:37 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The abstract model mapper.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 * @param <E> the element type
 * @param <D> the generic type
 */
public abstract class AbstractModelMapper<E extends Entity, D extends Dto<E>> {

    private Class<E> entityTypeClass;

    private Class<D> dtoTypeClass;

    /**
     * Instantiates a new abstract model mapper.
     *
     * @param entityTypeClass the entity type class
     * @param dtoTypeClass the dto type class
     */
    public AbstractModelMapper(final Class<E> entityTypeClass, final Class<D> dtoTypeClass) {

        this.entityTypeClass = entityTypeClass;
        this.dtoTypeClass = dtoTypeClass;
    }

    /** The model mapper. */
    @Autowired
    protected ModelMapper modelMapper;

    /**
     * To entity.
     *
     * @param dto the dto
     * @return the e
     */
    public E toEntity(final D dto) {

        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, entityTypeClass);
    }

    /**
     * To entities.
     *
     * @param dtos the dtos
     * @return the list
     */
    public List<E> toEntities(final List<D> dtos) {

        if (CollectionUtils.isEmpty(dtos)) {
            return null;
        }
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the d
     */
    public D toDto(final E entity) {

        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, dtoTypeClass);
    }

    /**
     * To dtos.
     *
     * @param entities the entities
     * @return the list
     */
    public List<D> toDtos(final List<E> entities) {

        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
