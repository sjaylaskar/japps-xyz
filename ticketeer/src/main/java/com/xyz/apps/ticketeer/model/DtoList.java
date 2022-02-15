/*
* Id: DtoList.java 15-Feb-2022 2:32:21 am SubhajoyLaskar
* Copyright (©) 2022 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.xyz.apps.ticketeer.model;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * The dto list.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public abstract class DtoList<T extends Dto<?>> {

    /**
     * The list of dtos.
     *
     * @return the list
     */
    protected abstract List<T> dtos();

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    protected boolean isEmpty() {
        return CollectionUtils.isEmpty(dtos());
    }

    /**
     * Checks if is not empty.
     *
     * @param <D> the generic type
     * @param dtoList the dto list
     * @return true, if is not empty
     */
    public static <D extends Dto<?>> boolean isNotEmpty(final DtoList<D> dtoList) {
        return (dtoList != null && CollectionUtils.isNotEmpty(dtoList.dtos()));
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
