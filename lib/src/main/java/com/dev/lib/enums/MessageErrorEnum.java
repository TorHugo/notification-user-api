package com.dev.lib.enums;

public enum MessageErrorEnum {
    INVALID_PARAMETER_EXCEPTION("Invalid Parameter: "),
    OBJECT_IS_NULL_OR_EMPTY_EXCEPTION("Object is null or empty: ");

    private final String message;

    MessageErrorEnum(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
