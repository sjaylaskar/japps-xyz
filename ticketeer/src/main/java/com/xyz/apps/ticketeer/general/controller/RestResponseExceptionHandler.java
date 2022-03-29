/*
 * Id: RestResponseEntityExceptionHandler.java 10-Mar-2022 1:16:27 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.general.controller;

import java.util.MissingResourceException;

import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xyz.apps.ticketeer.general.service.LocalizedException;
import com.xyz.apps.ticketeer.util.MessageUtil;
import com.xyz.apps.ticketeer.util.RestResponse;


/**
 * The rest response entity exception handler.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@ControllerAdvice(basePackages = "com")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /**
     * Handle constraint violation exception.
     *
     * @param exception the exception
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class, MongoTransactionException.class})
    public ResponseEntity<?> handleConstraintViolationException(final Exception exception, final WebRequest request)
            throws Exception {

        final Throwable rootCause = ExceptionUtils.getRootCause(exception);
        if (rootCause instanceof ConstraintViolationException) {
            final ConstraintViolationException constraintViolationException = (ConstraintViolationException) rootCause;
            if (CollectionUtils.isEmpty(constraintViolationException.getConstraintViolations())) {
                return super.handleException(constraintViolationException, request);
            }
            return RestResponse.badRequest(
                MessageUtil.formatPropertyErrors(
                    messageSource,
                    constraintViolationException
                        .getConstraintViolations().stream()
                        .map(constraintViolation -> Pair.of(constraintViolation.getPropertyPath().toString(),
                            Pair.of(constraintViolation.getInvalidValue() == null ? "null" : constraintViolation.getInvalidValue()
                                .toString(),
                                constraintViolation.getMessage())))
                        .toList()));
        }
        return handleException(exception, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {

        if (exception.getBindingResult() == null || CollectionUtils.isEmpty(exception.getBindingResult().getFieldErrors())) {
            return super.handleMethodArgumentNotValid(exception, headers, status, request);
        }
        return RestResponse.badRequest(
            MessageUtil.formatPropertyErrors(
                messageSource,
                exception.getBindingResult().getFieldErrors()
                    .stream().map(fieldError -> Pair.of(fieldError.getField(),
                        Pair.of(fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString(),
                            fieldError.getDefaultMessage()))).toList()));
    }

    /**
     * Handle data integrity violation exception.
     *
     * @param rootCause the root cause
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(final DataIntegrityViolationException exception,
            final WebRequest request) {

        final String message = ExceptionUtils.getRootCause(exception).getLocalizedMessage();
        return StringUtils.containsIgnoreCase(message, "Duplicate")
               ? RestResponse.conflict(message)
               : RestResponse.badRequest(message);
    }

    /**
     * Handle illegal argument exception.
     *
     * @param exception the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException exception,
            final WebRequest request) {

        return RestResponse.badRequest(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
    }

    /**
     * Handle missing resource exception.
     *
     * @param exception the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({MissingResourceException.class})
    public ResponseEntity<?> handleMissingResourceException(final MissingResourceException exception,
            final WebRequest request) {

        return RestResponse.internalServerError(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
    }

    /**
     * Handle localized service exception.
     *
     * @param exception the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({LocalizedException.class})
    public ResponseEntity<?> handleServiceException(final LocalizedException exception,
            final WebRequest request) {

        return RestResponse.of(exception.httpStatus(), ExceptionUtils.getRootCause(exception).getLocalizedMessage());
    }
}
