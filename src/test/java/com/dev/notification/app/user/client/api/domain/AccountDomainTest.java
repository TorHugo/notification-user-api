package com.dev.notification.app.user.client.api.domain;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@UnitaryTest
class AccountDomainTest {

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Test";
    private static final String CORRECT_EMAIL = "test@example.com";
    private static final String INCORRECT_EMAIL = "test_example.com";
    private static final String CORRECT_PASSWORD = "Password@123";

    @Test
    @DisplayName("Should be instantiated Account with success.")
    void t1(){
        // Given && When
        final var account = Account.create(FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, false);
        // Then
        assertNotNull(account);
        assertNotNull(account.getIdentifier());
        assertEquals(FIRST_NAME, account.getFirstName());
        assertEquals(LAST_NAME, account.getLastName());
        assertEquals(CORRECT_EMAIL, account.getEmail());
        assertEquals(CORRECT_PASSWORD, account.getPassword());
        assertTrue(account.isActive());
        assertFalse(account.isConfirmed());
        assertFalse(account.isAdmin());
        assertNotNull(account.getCreatedAt());
        assertNull(account.getUpdatedAt());
    }

    @Test
    @DisplayName("Should be restore Account with success.")
    void t2(){
        // Given
        final var expectedIdentifier = IdentifierUtils.unique();
        final var expectedCurrentDate = LocalDateTime.now();
        // When
        final var account = Account.restore(expectedIdentifier, FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, true, false, false, expectedCurrentDate, null);
        // Then
        assertNotNull(account);
        assertEquals(expectedIdentifier, account.getIdentifier());
        assertEquals(FIRST_NAME, account.getFirstName());
        assertEquals(LAST_NAME, account.getLastName());
        assertEquals(CORRECT_EMAIL, account.getEmail());
        assertEquals(CORRECT_PASSWORD, account.getPassword());
        assertTrue(account.isActive());
        assertFalse(account.isConfirmed());
        assertFalse(account.isAdmin());
        assertEquals(expectedCurrentDate, account.getCreatedAt());
        assertNull(account.getUpdatedAt());
    }

    @Test
    @DisplayName("Should be return full name with success.")
    void t3(){
        // Given
        final var expectedFullName = FIRST_NAME + " " + LAST_NAME;
        // When
        final var account = Account.create(FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, false);
        // Then
        assertNotNull(account);
        assertEquals(expectedFullName, account.getFullName());
    }

    @Test
    @DisplayName("Should be confirmed account with success.")
    void t4(){
        // Given
        final var account = Account.create(FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, false);
        // When
        account.isConfirmedAccount();
        // Then
        assertNotNull(account);
        assertTrue(account.isConfirmed());
        assertNotNull(account.getUpdatedAt());
    }

    @Test
    @DisplayName("Should throws exception when account is already confirmed.")
    void t5(){
        // Given
        final var expectedIdentifier = IdentifierUtils.unique();
        final var expectedCurrentDate = LocalDateTime.now();
        final var account = Account.restore(expectedIdentifier, FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, true, false, true, expectedCurrentDate, expectedCurrentDate);
        final var expectedErrorMessage = "This account is already confirmed!";
        // When
        final var exception = assertThrows(DomainException.class, account::isConfirmedAccount);
        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throws exception when invalid email.")
    void t6(){
        // Given
        final var expectedErrorMessage = "This email is not valid.";
        // When
        final var exception = assertThrows(DomainException.class, ()-> Account.create(FIRST_NAME, LAST_NAME, INCORRECT_EMAIL, CORRECT_PASSWORD, false));
        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should be update password with success.")
    void t7(){
        // Given
        final var newPassword = "UpdatePassword1234#";
        // When
        final var account = Account.create(FIRST_NAME, LAST_NAME, CORRECT_EMAIL, CORRECT_PASSWORD, false);
        account.updatePassword(newPassword);
        // Then
        assertNotNull(account);
        assertEquals(newPassword, account.getPassword());
    }
}
