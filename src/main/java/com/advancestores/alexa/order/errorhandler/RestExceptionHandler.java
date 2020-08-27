package com.advancestores.alexa.order.errorhandler;

import com.advancestores.alexa.order.errorhandler.domain.ApiError;
import com.advancestores.alexa.order.exception.ServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Add custom exception handlers. Make sure "handleAllException" is used.
 */
@ControllerAdvice
public class RestExceptionHandler extends CatalogResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleInternalServerError(ServiceException e, WebRequest request) {
        return handleAllException(e, HttpStatus.INTERNAL_SERVER_ERROR, request, ApiError.builder()
                .message(resolveMessageSource("severe.error", e.getMessage()))
                .build());
    }
}
