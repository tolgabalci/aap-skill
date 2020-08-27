package com.advancestores.alexa.order.errorhandler;

import com.advancestores.alexa.order.errorhandler.domain.ApiError;
import com.advancestores.alexa.order.errorhandler.domain.ApiErrors;
import com.advancestores.alexa.order.exception.NotFoundException;
import com.advancestores.alexa.order.interceptor.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base rest exception handler for Catalog services that will cover common http errors.
 * Create a Exception handler for the service and extend this class.
 *
 * <ul>
 *     <li>Response is in application/json format</li>
 *     <li>Correlation id is added to the response header for debugging</li>
 *     <li>Internal error codes are mapped to http status codes</li>
 *     <li>Error message text is consolidated in resource bundle files (messages.properties)</li>
 *     <li>i18n support</li>
 * </ul>
 *
 * @see <a href="https://advanceautoparts.atlassian.net/wiki/x/mwc6Tg">Error handling guidelines</a>
 */
public class CatalogResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @ExceptionHandler({ValidationException.class, ConstraintViolationException.class})
    public final ResponseEntity<Object> validationExceptionHandler(Exception e, WebRequest request) {
        int code = 1001;
        if(e instanceof ConstraintViolationException){
            return handleAllException(e, HttpStatus.BAD_REQUEST, request, ((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map(ex -> ApiError.builder().code(code).message(resolveMessageSource(1001, (String)ex.getInvalidValue(), ex.getPropertyPath().toString())).build())
                    .collect(Collectors.toList()));
        }

        return handleAllException(e, HttpStatus.BAD_REQUEST, request, ApiError.builder()
                .code(1001).message(e.getMessage())
                .build());
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> notFoundHandler(NotFoundException e, WebRequest request) {
        int code = 1004;
        return handleAllException(e, HttpStatus.NOT_FOUND, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, e.getMessage()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        int code = 1001;
        return handleAllException(ex, HttpStatus.BAD_REQUEST, request, ex.getBindingResult()
                .getFieldErrors().stream()
                .map(e -> ApiError.builder()
                        .code(code).message(resolveMessageSource(code, (String)e.getRejectedValue(), e.getField()))
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request){
        int code = 1002;
        return handleAllException(e, HttpStatus.BAD_REQUEST, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, e.getParameterName()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        int code = 1002;
        return handleAllException(e, HttpStatus.BAD_REQUEST, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, e.getVariableName()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        int code = 1004;
        return handleAllException(ex, HttpStatus.NOT_FOUND, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, ex.getRequestURL()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
	int code = 1001;
        String message = null;

        if (e instanceof MethodArgumentTypeMismatchException) {
            message = resolveMessageSource(code, e.getValue().toString(), ((MethodArgumentTypeMismatchException) e).getName());
        } else {
            message = resolveMessageSource(code, e.getValue().toString(), e.getPropertyName());
        }

        var apiError = ApiError.builder().code(code).message(message).build();

        return handleAllException(e, HttpStatus.BAD_REQUEST, request, apiError);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        int code = 1005;
        return handleAllException(e, HttpStatus.BAD_REQUEST, request, ApiError.builder()
                .code(code).message(resolveMessageSource(1005, e.getMessage()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        int code = 1006;
        return handleAllException(ex, HttpStatus.NOT_ACCEPTABLE, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, request.getHeader(HttpHeaders.ACCEPT), ex.getSupportedMediaTypes().stream().map(MimeType::toString).collect(Collectors.toList()).toString()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        int code = 1006;
        return handleAllException(ex, HttpStatus.NOT_ACCEPTABLE, request, ApiError.builder()
                .code(code).message(resolveMessageSource(code, request.getHeader(HttpHeaders.ACCEPT), ex.getSupportedMediaTypes().stream().map(MimeType::toString).collect(Collectors.toList()).toString()))
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(RequestInterceptor.HEADER_X_CORRELATION_ID, MDC.get(RequestInterceptor.HEADER_X_CORRELATION_ID));
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    protected ResponseEntity<Object> handleAllException(Exception e, HttpStatus status, WebRequest request, List<ApiError> apiErrors) {
        return handleExceptionInternal(e, ApiErrors.builder().errors(apiErrors).build(), new HttpHeaders(), status, request);
    }

    protected ResponseEntity<Object> handleAllException(Exception e, HttpStatus status, WebRequest request, ApiError apiError) {
        return handleAllException(e, status, request, List.of(apiError));
    }

    protected String  resolveMessageSource(int prop, String... args) {
        return resolveMessageSource(String.valueOf(prop), args);
    }

    protected String  resolveMessageSource(String prop, String... args) {
        return messageSource.getMessage(prop, args, LocaleContextHolder.getLocale());
    }

}
