package com.dev.domain.value.object;

import com.dev.domain.ValueObject;
import com.dev.lib.exception.template.InvalidParameterException;

import java.util.Objects;
import java.util.regex.Pattern;

public record Password(String value) implements ValueObject<String> {

    /**
     * Regular expression pattern used to validate passwords according to specific criteria.
     *
     * <p>This pattern ensures that the password meets the following requirements:</p>
     * <ul>
     *     <li>{@code \d}: At least one digit.</li>
     *     <li>{@code [a-z] }: At least one lowercase letter.</li>
     *     <li>{@code [A-Z] }: At least one uppercase letter.</li>
     *     <li>{@code [\W_] }: At least one special character or underscore.</li>
     *     <li>{@code .{8,} }: Minimum length of 8 characters.</li>
     * </ul>
     *
     * <p>The pattern is composed as follows:</p>
     * <ol>
     *     <li>{@code ^...$ }: Asserts the start and end of the string, ensuring the entire string is evaluated.</li>
     *     <li>{@code (?=.*\d) }: Positive lookahead assertion for at least one digit.</li>
     *     <li>{@code (?=.*[a-z]) }: Positive lookahead assertion for at least one lowercase letter.</li>
     *     <li>{@code (?=.*[A-Z]) }: Positive lookahead assertion for at least one uppercase letter.</li>
     *     <li>{@code (?=.*[\W_] )}: Positive lookahead assertion for at least one special character or underscore.</li>
     *     <li>{@code .{8,} }: Ensures the string has a minimum length of 8 characters.</li>
     * </ol>
     *
     * <p>If the password matches this pattern, it is considered valid. Otherwise, it fails the validation.</p>
     *
     * @see #isValidValue(String)
     */
    private static final String PASSWORD_VALIDATION_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}";

    public Password(final String value) {
        validateValue(value);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void validateValue(final String currentValue) {
        if (isValidValue(currentValue))
            throw new InvalidParameterException(currentValue);
    }
    private boolean isValidValue(final String currentValue) {
        return Pattern.compile(PASSWORD_VALIDATION_PATTERN)
                .matcher(currentValue)
                .matches();
    }
}