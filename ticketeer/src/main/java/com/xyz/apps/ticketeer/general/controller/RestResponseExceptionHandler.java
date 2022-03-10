/*
 * Id: RestResponseEntityExceptionHandler.java 10-Mar-2022 1:16:27 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.xyz.apps.ticketeer.general.controller;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * The rest response entity exception handler.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
@ControllerAdvice(basePackages = "com")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

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
            return ResponseEntity.badRequest().body(constraintViolationException
                .getConstraintViolations().stream()
                .map(constraintViolation -> formatPropertyError(constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getInvalidValue() == null ? null : constraintViolation.getInvalidValue().toString(),
                        constraintViolation.getMessage())).collect(Collectors.joining("\n")));
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
        return ResponseEntity.badRequest().body(
            exception.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> formatPropertyError(fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage())));
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
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ExceptionUtils.getRootCause(exception).getLocalizedMessage());
    }

    /**
     * Format property error.
     *
     * @param propertyName the property name
     * @param invalidValue the invalid value
     * @param message the message
     * @return the string
     */
    private static String formatPropertyError(final String propertyName, final String invalidValue, final String message) {

        return propertyName + "[" + invalidValue + "] => Error: " + message;
    }
}
