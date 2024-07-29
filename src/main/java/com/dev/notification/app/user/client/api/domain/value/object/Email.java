package com.dev.notification.app.user.client.api.domain.value.object;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;

import java.util.Objects;

public record Email(String value) implements ValueObject<String> {

    public Email(final String value) {
        validateValue(value);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void validateValue(final String currentValue) {
        if (isValidValue(currentValue))
            throw new DomainException("This email is not valid. Email:", currentValue);
    }

    private boolean isValidValue(final String email) {
        return !email.matches("^(.+)@(.+)$");
    }
}
