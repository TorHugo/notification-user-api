package com.dev.lib.exception;

public abstract class DefaultExceptionHandler extends RuntimeException {
    protected final String message;

    protected DefaultExceptionHandler(final String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}