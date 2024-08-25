package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.RedefinitionPasswordDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class RedefinitionPasswordIT {

    static final String FIRST_NAME = "firstName";
    static final String LAST_NAME = "lastName";
    static final String EMAIL = "email@example.com";
    static final String OLD_PASSWORD = "Password@123";
    static final String NEW_PASSWORD = "NewPassword@123";

    @Autowired
    private AccountGateway gateway;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private RedefinitionPassword useCase;

    @Test
    @DisplayName("Should be redefined password with success!")
    void t1(){
        // given
        final var dto = new RedefinitionPasswordDTO(EMAIL, OLD_PASSWORD, NEW_PASSWORD);
        final var oldPasswordEncryption = encryptionService.encryption(OLD_PASSWORD);
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, oldPasswordEncryption, false);
        gateway.save(account);

        // when
        useCase.execute(dto);
        final var accountWithNewPassword = gateway.findAccountByEmailWithThrows(EMAIL);

        // then
        assertNotNull(accountWithNewPassword);
        assertTrue(encryptionService.matches(NEW_PASSWORD, accountWithNewPassword.getPassword()));
    }

    @Test
    @DisplayName("Should throws exception (@DomainException) when this password is not the same as the saved one!")
    void t2(){
        // given
        final var expectedException = "The password passed is not the same as the saved one!";
        final var dto = new RedefinitionPasswordDTO(EMAIL, NEW_PASSWORD, NEW_PASSWORD);
        final var oldPasswordEncryption = encryptionService.encryption(OLD_PASSWORD);
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, oldPasswordEncryption, false);
        gateway.save(account);

        // when
        final var exception = assertThrows(DomainException.class, () -> useCase.execute(dto));

        // then
        assertNotNull(exception);
        assertEquals(expectedException, exception.getMessage());
    }
}
