package com.dev.domain.value.object;

import com.dev.domain.ValueObject;
import com.dev.lib.exception.template.InvalidParameterException;

import java.util.Objects;

public record Password(String value) implements ValueObject<String> {

    public Password(final String value) {
        validateValue(value);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void validateValue(final String currentValue) {
        if (currentValue.length() < 8)
            throw new InvalidParameterException("This password must be at least 8 characters.");
        if (!currentValue.matches(".*[A-Z].*"))
            throw new InvalidParameterException("This password does not contain uppercase letters.");
        if (!currentValue.matches(".*[a-z].*"))
            throw new InvalidParameterException("This password does not contain lowercase letters.");
        if (!currentValue.matches(".*\\W.*"))
            throw new InvalidParameterException("This password must have a special character.");
    }
}