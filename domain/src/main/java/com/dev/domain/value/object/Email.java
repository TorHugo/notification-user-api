package com.dev.domain.value.object;

import com.dev.domain.ValueObject;
import com.dev.lib.exception.template.InvalidParameterException;

import java.util.Objects;

public record Email(String value) implements ValueObject<String> {

    public Email(final String value) {
        validateValue(value);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void validateValue(final String currentValue) {
        if (isValidValue(currentValue))
            throw new InvalidParameterException(currentValue);
    }

    private boolean isValidValue(final String email) {
        return !email.matches("^(.+)@(.+)$");
    }
}
