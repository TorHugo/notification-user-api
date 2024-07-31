package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationIT
class SaveAccountUseCaseIT {
    @Autowired
    private SaveAccountUseCase saveAccountUseCase;

    @Autowired
    private AccountGateway accountGateway;

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Test";
    private static final String EMAIL = "Test@example.com";
    private static final String PASSWORD = "Password@123";

    @Test
    @DisplayName("Should save account with successfully.")
    void t1(){
        // Given
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, false);
        // When
        final var actualAccount = saveAccountUseCase.execute(account);
        assertNotNull(actualAccount);
        assertNotNull(actualAccount.getIdentifier());

        // Then
        final var savedAccount = accountGateway.findAccountByIdentifier(actualAccount.getIdentifier());
        assertNotNull(savedAccount);
        assertEquals(savedAccount.getIdentifier(), actualAccount.getIdentifier());
        assertEquals(savedAccount.getFirstName(), actualAccount.getFirstName());
        assertEquals(savedAccount.getLastName(), actualAccount.getLastName());
        assertEquals(savedAccount.getEmail(), actualAccount.getEmail());
        assertEquals(savedAccount.getPassword(), actualAccount.getPassword());
        assertEquals(savedAccount.isActive(), actualAccount.isActive());
        assertEquals(savedAccount.isAdmin(), actualAccount.isAdmin());
        assertEquals(savedAccount.isConfirmed(), actualAccount.isConfirmed());
        assertNotNull(savedAccount.getCreatedAt());
        assertEquals(savedAccount.getUpdatedAt(), actualAccount.getUpdatedAt());
    }
}
