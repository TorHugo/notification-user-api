
package com.dev.lib.enums;

public enum MessagePropertiesEnum {
    TO_EQUALS("Must values be to equals!"),
    TO_NOT_EQUALS("Must not values be to equals!"),
    TO_TRUE("Must value be to true!"),
    TO_FALSE("Must value be to false!"),
    TO_NULL("Must value be to null!"),
    TO_NOT_NULL("Must not value be to null!");

    private final String message;

    MessagePropertiesEnum(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
