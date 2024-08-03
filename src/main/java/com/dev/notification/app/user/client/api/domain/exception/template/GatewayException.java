package com.dev.notification.app.user.client.api.domain.exception.template;

import com.dev.notification.app.user.client.api.domain.exception.DomainExceptionHandler;

public class GatewayException extends DomainExceptionHandler {
    public GatewayException(final String message,
                            final String parameter) {
        super(message, parameter);
    }
    public GatewayException(final String message) {
        super(message);
    }
}
