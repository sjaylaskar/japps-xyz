/*
* Id: DtoList.java 15-Feb-2022 2:32:21 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.general.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 * @param <T> the generic type
 */
@Getter
@Setter
@ToString
public class DtoList<T extends Dto<?>> {

    /** The dtos. */
    private List<T> dtos = new ArrayList<>();

    /**
     * Instantiates a new dto list.
     */
    public DtoList() {

    }

    /**
     * Instantiates a new dto list.
     *
     * @param dtos the dtos
     */
    public DtoList(final List<T> dtos) {
        this.dtos.addAll(dtos);
    }

    /**
     * The list of dtos.
     *
     * @return the list
     */
    public List<T> dtos() {
        return dtos;
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(dtos());
    }

    /**
     * Checks if is not empty.
     *
     * @return true, if is not empty
     */
    public boolean isNotEmpty() {

        return !isEmpty();
    }

    /**
     * Checks if is not empty.
     *
     * @param <D> the generic type
     * @param dtoList the dto list
     * @return true, if is not empty
     */
    public static <D extends Dto<?>> boolean isNotEmpty(final DtoList<D> dtoList) {
        return (dtoList != null && dtoList.isNotEmpty());
    }

    /**
     * Checks if is empty.
     *
     * @param <D> the generic type
     * @param dtoList the dto list
     * @return true, if is empty
     */
    public static <D extends Dto<?>> boolean isEmpty(final DtoList<D> dtoList) {
        return !isNotEmpty(dtoList);
    }
}
