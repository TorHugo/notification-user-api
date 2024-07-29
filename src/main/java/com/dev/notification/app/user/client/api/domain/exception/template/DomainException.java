package com.dev.notification.app.user.client.api.domain.exception.template;

import com.dev.notification.app.user.client.api.domain.exception.DomainExceptionHandler;

public class DomainException extends DomainExceptionHandler {
    public DomainException(final String message,
                           final String parameter) {
        super(message, parameter);
    }

    public DomainException(final String message) {
        super(message);
    }
}
