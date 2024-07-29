package com.dev.notification.app.user.client.api.domain.exception;

public abstract class DomainExceptionHandler extends RuntimeException {
    private final String message;

    protected DomainExceptionHandler(final String message,
                                     final String parameter) {
        super(message.concat(" | ").concat(parameter));
        this.message = message;
    }

    protected DomainExceptionHandler(final String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}