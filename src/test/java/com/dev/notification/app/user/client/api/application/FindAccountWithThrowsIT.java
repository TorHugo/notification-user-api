package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class FindAccountWithThrowsIT {

    static final String FIRST_NAME = "firstName";
    static final String LAST_NAME = "lastName";
    static final String EMAIL = "email@example.com";
    static final String PASSWORD = "Password@123";

    @Autowired
    private AccountGateway gateway;

    @Autowired
    private FindAccountWithThrows useCase;

    @Test
    @DisplayName("Should find account with success!")
    void t1() {
        // given
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, false);
        gateway.save(account);

        // when
        final var savedAccount = useCase.execute(account.getEmail());

        // then
        assertNotNull(savedAccount);
        assertEquals(account.getFirstName(), savedAccount.getFirstName());
        assertEquals(account.getLastName(), savedAccount.getLastName());
        assertEquals(account.getEmail(), savedAccount.getEmail());
        assertEquals(account.getPassword(), savedAccount.getPassword());
        assertEquals(account.isAdmin(), savedAccount.isAdmin());
        assertEquals(account.isConfirmed(), savedAccount.isConfirmed());
        assertEquals(account.isActive(), savedAccount.isActive());
        assertEquals(account.getCreatedAt(), savedAccount.getCreatedAt());
    }

    @Test
    @DisplayName("Should throws a exception (@GatewayException) when account not found!")
    void t2() {
        // given && when
        final var expectedMessage = "Account not found!";
        final var exception = assertThrows(GatewayException.class, () -> useCase.execute(EMAIL));

        // then
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
}

