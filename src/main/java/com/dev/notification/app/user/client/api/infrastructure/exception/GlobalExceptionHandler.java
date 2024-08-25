package com.dev.notification.app.user.client.api.infrastructure.exception;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.exception.template.EventException;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.exception.template.ServiceException;
import com.dev.notification.app.user.client.api.infrastructure.exception.models.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerDomainException(final DomainException ex,
                                                    final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerServiceException(final ServiceException ex,
                                                     final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(EventException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handlerEventException(final EventException ex,
                                                   final HttpServletRequest request) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handlerGatewayException(final GatewayException ex,
                                                     final HttpServletRequest request) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
