package com.dev.notification.app.user.client.api.domain.exception.template;

import com.dev.notification.app.user.client.api.domain.exception.DomainExceptionHandler;

public class EventException extends DomainExceptionHandler {
    public EventException(final String message) {
        super(message);
    }
}
