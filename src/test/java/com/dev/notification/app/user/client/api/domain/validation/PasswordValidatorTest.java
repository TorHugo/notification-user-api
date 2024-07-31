package com.dev.notification.app.user.client.api.domain.validation;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.validation.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@UnitaryTest
class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @Test
    @DisplayName("Should be to return true when password is valid.")
    void t1(){
        //Given
        final var expectedPassword = "Password#";
        //When
        final var result = validator.isValid(expectedPassword, null);
        //Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should be throws exception when password is null.")
    void t2(){
        //Given
        final String expectedMessageError = "Password cannot be null!";
        //When
        final var result = assertThrows(DomainException.class, () -> validator.isValid(null, null));
        //Then
        assertNotNull(result);
        assertEquals(expectedMessageError, result.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"size", "password", "PASSWORD", "Password"})
    @DisplayName("Should throws exception when invalid passwords.")
    void t2(final String incorrectPassword) {
        // Given
        final var expectedMessage = getExpectedMessageForPassword(incorrectPassword);

        // When
        final var exception = assertThrows(DomainException.class, () -> validator.isValid(incorrectPassword, null));

        // Then
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    private String getExpectedMessageForPassword(final String password) {
        return switch (password) {
            case "size" -> "This password must be at least 8 characters.";
            case "password" -> "This password does not contain uppercase letters.";
            case "PASSWORD" -> "This password does not contain lowercase letters.";
            default -> "This password must have a special character.";
        };
    }
}
