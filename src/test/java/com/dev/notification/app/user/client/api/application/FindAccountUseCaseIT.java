package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@IntegrationIT
class FindAccountUseCaseIT {
    @Autowired
    private FindAccountUseCase findAccountUseCase;

    @Autowired
    private AccountGateway accountGateway;

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Test";
    private static final String EMAIL = "Test@example.com";
    private static final String PASSWORD = "Password@123";

    @Test
    @DisplayName("Should find account with successfully.")
    void t1(){
        // Given
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, false);
        final var savedAccount = accountGateway.save(account);
        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getEmail());
        // When
        final var actualAccount = findAccountUseCase.execute(savedAccount.getEmail());
        // Then
        assertNotNull(actualAccount);
        assertEquals(actualAccount.getIdentifier(), savedAccount.getIdentifier());
        assertEquals(actualAccount.getFirstName(), savedAccount.getFirstName());
        assertEquals(actualAccount.getLastName(), savedAccount.getLastName());
        assertEquals(actualAccount.getEmail(), savedAccount.getEmail());
        assertEquals(actualAccount.getPassword(), savedAccount.getPassword());
        assertEquals(actualAccount.isActive(), savedAccount.isActive());
        assertEquals(actualAccount.isAdmin(), savedAccount.isAdmin());
        assertEquals(actualAccount.isConfirmed(), savedAccount.isConfirmed());
        assertNotNull(actualAccount.getCreatedAt());
        assertEquals(actualAccount.getUpdatedAt(), savedAccount.getUpdatedAt());
    }

    @Test
    @DisplayName("Should not throws exception when account not found.")
    void t2(){
        // Given && When
        final var actualAccount = findAccountUseCase.execute(IdentifierUtils.unique());
        // Then
        assertNull(actualAccount);
    }
}
