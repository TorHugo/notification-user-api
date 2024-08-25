package com.dev.notification.app.user.client.api.domain.exception.template;

import com.dev.notification.app.user.client.api.domain.exception.DomainExceptionHandler;

public class ServiceException extends DomainExceptionHandler {
    public ServiceException(final String message) {
        super(message);
    }
}
