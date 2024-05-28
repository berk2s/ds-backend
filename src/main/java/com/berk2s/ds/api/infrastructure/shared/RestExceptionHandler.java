package com.berk2s.ds.api.infrastructure.shared;

import com.berk2s.ds.api.infrastructure.security.jwt.InvalidJWT;
import com.berk2s.ds.api.infrastructure.security.jwt.JWTException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse>  handleConstraintViolation(RuntimeException ex) {
        log.warn("RuntimeException: {}", ex.getMessage());
        return new ResponseEntity<>(createError("invalid_request", ex.getMessage(), List.of("")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJWT.class)
    protected ResponseEntity<ErrorResponse>  handleInvalidJWT(InvalidJWT ex) {
        log.warn("InvalidJWT: {}", ex.getMessage());
        return new ResponseEntity<>(createError("invalid_request", ex.getMessage(), List.of("")),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JWTException.class)
    protected ResponseEntity<ErrorResponse>  handleJWTException(JWTException ex) {
        log.warn("InvalidJWT: {}", ex.getMessage());
        return new ResponseEntity<>(createError("server_error", ex.getMessage(), List.of("")),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse>  handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("ResourceNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(createError("invalid_request", ex.getMessage(), List.of("")),
        HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createError(String type, String desc, List<String> errorMessages) {
        return ErrorResponse.builder()
                .error(type)
                .errorDescription(desc)
                .details(errorMessages)
                .build();
    }
}
