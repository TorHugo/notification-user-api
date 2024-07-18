package com.dev.infrastructure.exception;

import com.dev.domain.exception.template.InvalidArgumentException;
import com.dev.domain.exception.template.InvalidParameterException;
import com.dev.domain.exception.template.NotFoundException;
import com.dev.domain.exception.template.ObjectIsNullOrEmptyException;
import com.dev.infrastructure.exception.models.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ExceptionResponse handleInvalidArgumentError(final InvalidArgumentException ex,
                                                        final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ExceptionResponse handleInvalidParameterError(final InvalidParameterException ex,
                                                         final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleNotFoundError(final NotFoundException ex,
                                                 final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ObjectIsNullOrEmptyException.class)
    public ExceptionResponse handleObjectIsNullOrEmptyError(final NotFoundException ex,
                                                            final HttpServletRequest request) {
        return ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(BAD_REQUEST.value())
                    .error(BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();
    }
}
