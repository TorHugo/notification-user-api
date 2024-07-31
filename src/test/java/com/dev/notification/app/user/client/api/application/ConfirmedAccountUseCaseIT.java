package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedAccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class ConfirmedAccountUseCaseIT {
    @Autowired
    private ConfirmedAccountUseCase confirmedAccountUseCase;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private NotificationGateway notificationGateway;

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Test";
    private static final String EMAIL = "email@example.com";
    private static final String PASSWORD = "Password@123";

    private static final String TEMPLATE = "template";
    private static final String HASH = "value";
    private static final String EXPIRATION_DATE = "2024-12-31T23:59:59.000000000";
    private static final String OLD_EXPIRATION_DATE = "2024-01-01T23:59:59.000000000";
    private static final List<Parameter> PARAMETERS = List.of(new Parameter("hashcode", HASH), new Parameter("expiration-date", EXPIRATION_DATE));
    private static final List<Parameter> PARAMETERS_OLD_EXPIRATION_DATE = List.of(new Parameter("hashcode", HASH), new Parameter("expiration-date", OLD_EXPIRATION_DATE));

    @Test
    @DisplayName("Should confirmed account with successfully.")
    void t1(){
        // Given
        final var account = Account.create(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, false);
        final var notification = Notification.create(EMAIL, TEMPLATE, PARAMETERS);
        final var savedAccount = accountGateway.save(account);
        notificationGateway.save(notification);
        final var savedNotification = notificationGateway.findByContact(EMAIL);
        assertNotNull(savedAccount);
        assertNotNull(savedNotification);
        // When
        confirmedAccountUseCase.execute(new ConfirmedAccountDTO(HASH, EMAIL));
        // Then
        final var newAccount = accountGateway.findAccountByIdentifier(savedAccount.getIdentifier());
        assertNotNull(newAccount);
        assertTrue(newAccount.isConfirmed());
    }

    @Test
    @DisplayName("Should throws exception (@GatewayException) when contact not found.")
    void t2(){
        // Given
        final var expectedErrorMessage = "Notification not found, for this contact.";
        // When
        final var exception = assertThrows(GatewayException.class, ()-> confirmedAccountUseCase.execute(new ConfirmedAccountDTO(HASH, EMAIL)));
        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception (@DomainException) when hashcode is not valid.")
    void t3() {
        // Given
        final var expectedErrorMessage = "This hashcode is not the same as the saved one!";
        final var notificationCorrectEmail = Notification.create(EMAIL, TEMPLATE, PARAMETERS);
        notificationGateway.save(notificationCorrectEmail);
        final var savedNotification = notificationGateway.findByContact(EMAIL);
        assertNotNull(savedNotification);

        // When
        final var exception = assertThrows(DomainException.class, () -> confirmedAccountUseCase.execute(new ConfirmedAccountDTO("invalid_hash", EMAIL)));

        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception (@DomainException) when hashcode is expired.")
    void t4() {
        // Given
        final var expectedErrorMessage = "This hashcode is expired!";
        final var notificationCorrectEmail = Notification.create(EMAIL, TEMPLATE, PARAMETERS_OLD_EXPIRATION_DATE);
        notificationGateway.save(notificationCorrectEmail);
        final var savedNotification = notificationGateway.findByContact(EMAIL);
        assertNotNull(savedNotification);

        // When
        final var exception = assertThrows(DomainException.class, () -> confirmedAccountUseCase.execute(new ConfirmedAccountDTO(HASH, EMAIL)));

        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception (@GatewayException) when account not found.")
    void t5() {
        // Given
        final var expectedErrorMessage = "Account not found!";
        final var notificationCorrectEmail = Notification.create(EMAIL, TEMPLATE, PARAMETERS);
        notificationGateway.save(notificationCorrectEmail);
        final var savedNotification = notificationGateway.findByContact(EMAIL);
        assertNotNull(savedNotification);

        // When
        final var exception = assertThrows(GatewayException.class, () -> confirmedAccountUseCase.execute(new ConfirmedAccountDTO(HASH, EMAIL)));

        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception (@DomainException) when this account is already confirmed.")
    void t6() {
        // Given
        final var expectedErrorMessage = "This account is already confirmed!";

        final var account = Account.restore(IdentifierUtils.unique(), FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, true, false, true, LocalDateTime.now(), null);
        final var notification = Notification.create(EMAIL, TEMPLATE, PARAMETERS);
        final var savedAccount = accountGateway.save(account);
        notificationGateway.save(notification);
        final var savedNotification = notificationGateway.findByContact(EMAIL);
        assertNotNull(savedAccount);
        assertNotNull(savedNotification);

        // When
        final var exception = assertThrows(DomainException.class, () -> confirmedAccountUseCase.execute(new ConfirmedAccountDTO(HASH, EMAIL)));

        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
