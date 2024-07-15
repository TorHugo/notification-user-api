package com.dev.domain.entity;

import com.dev.lib.enums.MessageErrorEnum;
import com.dev.lib.exception.template.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static com.dev.lib.enums.MessagePropertiesEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    final String firstName = "Test";
    final String lastName = "Test";
    final String correctEmail = "email@test.com";
    final String correctPassword = "CorrectPassword123@";
    final String incorrectEmail = "email";
    final boolean isNotAdmin = false;

    @Test
    @DisplayName("Must create the domain successfully!")
    void shouldCreateAccountWhenValidParameters() {
        // Given && When
        final var account = Account.create(
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isNotAdmin
        );

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertNotNull(account.getIdentifier(), TO_NOT_NULL.getMessage());
        assertEquals(firstName, account.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(lastName, account.getLastName(), TO_EQUALS.getMessage());
        assertEquals(correctEmail, account.getEmail(), TO_EQUALS.getMessage());
        assertEquals(correctPassword, account.getPassword(), TO_EQUALS.getMessage());
        Assertions.assertTrue(account.isActive(), TO_TRUE.getMessage());
        Assertions.assertFalse(account.isAdmin(), TO_FALSE.getMessage());
        Assertions.assertFalse(account.isConfirmed(), TO_FALSE.getMessage());
        assertNotNull(account.getCreatedAt(), TO_NOT_NULL.getMessage());
        Assertions.assertNull(account.getUpdatedAt(), TO_NULL.getMessage());
        assertNotNull(account.getLastAccess(), TO_NOT_NULL.getMessage());
    }

    @Test
    @DisplayName("Must restore the domain successfully!")
    void shouldRestoreAccountWhenValidParameters() {
        // Given
        final var identifier = AccountIdentifier.unique();
        final var isActiveAndConfirmed = true;
        final var currentDate = LocalDateTime.now();
        // When
        final var account = Account.restore(
                identifier,
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isActiveAndConfirmed,
                isNotAdmin,
                isActiveAndConfirmed,
                currentDate,
                null,
                currentDate);

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertEquals(identifier, account.getIdentifier(), TO_EQUALS.getMessage());
        assertEquals(firstName, account.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(lastName, account.getLastName(), TO_EQUALS.getMessage());
        assertEquals(correctEmail, account.getEmail(), TO_EQUALS.getMessage());
        assertEquals(correctPassword, account.getPassword(), TO_EQUALS.getMessage());
        assertEquals(isActiveAndConfirmed, account.isActive(), TO_EQUALS.getMessage());
        assertEquals(isNotAdmin, account.isAdmin(), TO_EQUALS.getMessage());
        assertEquals(isActiveAndConfirmed, account.isConfirmed(), TO_EQUALS.getMessage());
        assertEquals(currentDate, account.getCreatedAt(), TO_EQUALS.getMessage());
        Assertions.assertNull(account.getUpdatedAt(), TO_NULL.getMessage());
        assertEquals(currentDate, account.getLastAccess(), TO_EQUALS.getMessage());
    }

    @Test
    @DisplayName("Must update the domain successfully!")
    void shouldUpdateAccountWhenValidParameters() {
        // Given
        final var newFirstName = "New First Name";
        final var newPassword = "New Password";
        // When
        final var account = Account.create(
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isNotAdmin
        );

        final var updatedAccount = account.update(
            newFirstName,
            account.getLastName(),
            account.getEmail(),
            newPassword
        );

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertNotNull(updatedAccount, TO_NOT_NULL.getMessage());
        assertEquals(updatedAccount.getIdentifier(), account.getIdentifier(), TO_EQUALS.getMessage());
        assertEquals(newFirstName, updatedAccount.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(lastName, updatedAccount.getLastName(), TO_EQUALS.getMessage());
        assertEquals(correctEmail, updatedAccount.getEmail(), TO_EQUALS.getMessage());
        assertEquals(newPassword, updatedAccount.getPassword(), TO_EQUALS.getMessage());
        assertEquals(updatedAccount.isActive(), account.isActive(), TO_EQUALS.getMessage());
        assertEquals(updatedAccount.isAdmin(), account.isAdmin(), TO_EQUALS.getMessage());
        assertEquals(updatedAccount.isConfirmed(), account.isConfirmed(), TO_EQUALS.getMessage());
        assertEquals(updatedAccount.getCreatedAt(), account.getCreatedAt(), TO_EQUALS.getMessage());
        assertNotNull(updatedAccount.getUpdatedAt(), TO_NOT_NULL.getMessage());
        assertEquals(updatedAccount.getLastAccess(), account.getLastAccess(), TO_EQUALS.getMessage());
    }

    @Test
    @DisplayName("Must inactive the domain successfully!")
    void MustInactiveAccount() {
        // Given
        final var account = Account.create(
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isNotAdmin
        );

        // When
        final var inactivatedAccount = account.inactiveAccount();

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertNotNull(inactivatedAccount, TO_NOT_NULL.getMessage());
        assertEquals(inactivatedAccount.getIdentifier(), account.getIdentifier(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.getFirstName(), account.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.getLastName(), account.getLastName(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.getEmail(), account.getEmail(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.getPassword(), account.getPassword(), TO_EQUALS.getMessage());
        Assertions.assertFalse(inactivatedAccount.isActive(), TO_FALSE.getMessage());
        assertEquals(inactivatedAccount.isAdmin(), account.isAdmin(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.isConfirmed(), account.isConfirmed(), TO_EQUALS.getMessage());
        assertEquals(inactivatedAccount.getCreatedAt(), account.getCreatedAt(), TO_EQUALS.getMessage());
        assertNotNull(inactivatedAccount.getUpdatedAt(), TO_NOT_NULL.getMessage());
        assertEquals(inactivatedAccount.getLastAccess(), account.getLastAccess(), TO_EQUALS.getMessage());
    }

    @Test
    @DisplayName("Must confirmed the domain successfully!")
    void MustConfirmedAccount() {
        // Given
        final var account = Account.create(
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isNotAdmin
        );

        // When
        final var confirmedAccount = account.confirmeAccount();

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertNotNull(confirmedAccount, TO_NOT_NULL.getMessage());
        assertEquals(confirmedAccount.getIdentifier(), account.getIdentifier(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.getFirstName(), account.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.getLastName(), account.getLastName(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.getEmail(), account.getEmail(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.getPassword(), account.getPassword(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.isActive(), account.isActive(), TO_EQUALS.getMessage());
        assertEquals(confirmedAccount.isAdmin(), account.isAdmin(), TO_EQUALS.getMessage());
        Assertions.assertTrue(confirmedAccount.isConfirmed(), TO_TRUE.getMessage());
        assertEquals(confirmedAccount.getCreatedAt(), account.getCreatedAt(), TO_EQUALS.getMessage());
        assertNotNull(confirmedAccount.getUpdatedAt(), TO_NOT_NULL.getMessage());
        assertEquals(confirmedAccount.getLastAccess(), account.getLastAccess(), TO_EQUALS.getMessage());
    }

    @Test
    @DisplayName("Must update last access the domain successfully!")
    void MustUpdateLastAccess() {
        // Given
        final var account = Account.create(
                firstName,
                lastName,
                correctEmail,
                correctPassword,
                isNotAdmin
        );

        // When
        final var lastAccess = account.lastAccess();

        // Then
        assertNotNull(account, TO_NOT_NULL.getMessage());
        assertNotNull(lastAccess, TO_NOT_NULL.getMessage());
        assertEquals(lastAccess.getIdentifier(), account.getIdentifier(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.getFirstName(), account.getFirstName(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.getLastName(), account.getLastName(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.getEmail(), account.getEmail(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.getPassword(), account.getPassword(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.isActive(), account.isActive(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.isAdmin(), account.isAdmin(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.isConfirmed(), account.isConfirmed(), TO_EQUALS.getMessage());
        assertEquals(lastAccess.getCreatedAt(), account.getCreatedAt(), TO_EQUALS.getMessage());
        assertNotNull(lastAccess.getUpdatedAt(), TO_NOT_NULL.getMessage());
        assertNotNull(lastAccess.getLastAccess(), TO_NOT_NULL.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when invalid emails!")
    void shouldThrowExceptionWhenInvalidEmail() {
        // Given
        final var expectedMessage = MessageErrorEnum.INVALID_PARAMETER_EXCEPTION.getMessage().concat(incorrectEmail);

        // When
        final var exception = assertThrows(InvalidParameterException.class, ()->
            Account.create(
                firstName,
                lastName,
                incorrectEmail,
                correctPassword,
                isNotAdmin
        ));

        // Then
        assertNotNull(exception, TO_NOT_NULL.getMessage());
        assertEquals(exception.getMessage(), expectedMessage, TO_EQUALS.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"size", "password", "PASSWORD", "Password"})
    void shouldThrowExceptionBasedOnPasswordCriteria(final String incorrectPassword) {
        // Given
        final var expectedMessage = MessageErrorEnum.INVALID_PARAMETER_EXCEPTION.getMessage()
                .concat(getExpectedMessageForPassword(incorrectPassword));

        // When
        final var exception = assertThrows(InvalidParameterException.class, () ->
                Account.create(firstName, lastName, correctEmail, incorrectPassword, isNotAdmin));

        // Then
        assertNotNull(exception, TO_NOT_NULL.getMessage());
        assertEquals(exception.getMessage(), expectedMessage, TO_EQUALS.getMessage());
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