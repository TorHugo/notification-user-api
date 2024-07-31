package com.dev.notification.app.user.client.api.domain.value.object;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;

import java.util.Objects;

public record Email(String value) {

    public Email(final String value) {
        validateValue(value);
        this.value = Objects.requireNonNull(value);
    }

    public void validateValue(final String currentValue) {
        if (!currentValue.matches("^(.+)@(.+)$"))
            throw new DomainException("This email is not valid. Email:", currentValue);
    }
}
