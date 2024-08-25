package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.exception.template.ServiceException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.dev.notification.app.user.client.api.domain.enums.TemplateMessage.CONFIRMED_ACCOUNT;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class AccountServiceIT {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private NotificationGateway notificationGateway;

    @Autowired
    private EventGateway eventGateway;

    @Autowired
    private HashTokenGateway hashTokenGateway;

    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String EMAIL = "email@example.com";
    private static final String PASSWORD = "Password@123";

    private static final EventType EXPECTED_EVENT_TYPE = EventType.CREATE_ACCOUNT_EVENT;

    @Test
    @DisplayName("Should create account with success.")
    void t1(){
        // Given && When
        final var account = service.create(new CreateAccountDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD));
        assertNotNull(account);
        final var savedAccount = accountGateway.findAccountByIdentifier(account.getIdentifier());
        final var savedNotification = notificationGateway.findByContact(savedAccount.getEmail());
        final var savedEvent = eventGateway.findByAggregateIdentifier(savedAccount.getIdentifier());

        // Then
        assertAccount(savedAccount);
        assertNotification(savedNotification);
        assertEvent(savedEvent, savedAccount.getIdentifier());
    }

    @Test
    @DisplayName("Should throws exception (@ServiceException and @GatewayException) when account already exists!")
    void t2(){
        // Given
        final var expectedException = "This account already exists! With email!";
        final var expectedEventException = "Event not found with aggregate identifier.";
        final var expectedNotificationException = "Notification not found, for this contact.";
        final var expectedAccount = Account.create(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, false);
        accountGateway.save(expectedAccount);

        //  When
        final var exception = assertThrows(ServiceException.class, () -> service.create(new CreateAccountDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD)));
        final var notificationException = assertThrows(GatewayException.class, () -> notificationGateway.findByContact(EMAIL));
        final var eventException = assertThrows(GatewayException.class, () -> eventGateway.findByAggregateIdentifier(expectedAccount.getIdentifier()));

        // Then
        assertNotNull(exception);
        assertNotNull(eventException);
        assertNotNull(notificationException);
        assertEquals(expectedException, exception.getMessage());
        assertEquals(expectedEventException, eventException.getMessage());
        assertEquals(expectedNotificationException, notificationException.getMessage());
    }

    @Test
    @DisplayName("Should confirmed account with success!")
    void t3(){
        // Given
        final var account = service.create(new CreateAccountDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD));
        assertNotNull(account);
        assertFalse(account.isConfirmed());
        final var savedNotification = notificationGateway.findByContact(account.getEmail());
        final var hashCode = savedNotification.getParameters().stream().filter(notification -> Objects.equals(notification.name(), "hashcode")).findFirst().orElse(null);
        assertNotNull(hashCode);

        //  When
        service.confirmed(new ConfirmedHashDTO(hashCode.value(), EMAIL));
        final var confirmedAccount = accountGateway.findAccountByEmailWithThrows(EMAIL);

        // Then
        assertNotNull(confirmedAccount);
        assertTrue(confirmedAccount.isConfirmed());
    }

    @Test
    @DisplayName("Should throws exception (@ServiceException) when hash token does not exists!")
    void t4(){
        // Given
        final var expectedException = "This hash-token does not exists!";
        final var account = service.create(new CreateAccountDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD));
        assertNotNull(account);
        assertFalse(account.isConfirmed());
        hashTokenGateway.delete("confirmed-account", account.getIdentifier());

        //  When
        final var exception = assertThrows(ServiceException.class, () -> service.confirmed(new ConfirmedHashDTO("12345", EMAIL)));
        final var confirmedAccount = accountGateway.findAccountByEmailWithThrows(EMAIL);

        // Then
        assertNotNull(exception);
        assertEquals(expectedException, exception.getMessage());
        assertNotNull(confirmedAccount);
        assertFalse(confirmedAccount.isConfirmed());
    }

    private void assertAccount(final Account savedAccount) {
        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getIdentifier());
        assertEquals(FIRST_NAME, savedAccount.getFirstName());
        assertEquals(LAST_NAME, savedAccount.getLastName());
        assertEquals(EMAIL, savedAccount.getEmail());
        assertNotEquals(PASSWORD, savedAccount.getPassword());
        assertTrue(savedAccount.isActive());
        assertFalse(savedAccount.isConfirmed());
        assertFalse(savedAccount.isAdmin());
        assertNotNull(savedAccount.getCreatedAt());
    }

    private void assertNotification(final Notification savedNotification) {
        assertNotNull(savedNotification);
        assertNotNull(savedNotification.getIdentifier());
        assertEquals(EMAIL, savedNotification.getContact());
        assertEquals(CONFIRMED_ACCOUNT.getTemplate(), savedNotification.getTemplate());
        assertNotNull(savedNotification.getParameters());
        final var parameters = savedNotification.getParameters().stream().findFirst().orElse(null);
        assertNotNull(parameters);
        assertNotNull(parameters.name());
        assertNotNull(parameters.value());
        assertNotNull(savedNotification.getCreatedAt());
    }

    private void assertEvent(final Event savedEvent,
                             final String aggregateIdentifier) {
        assertNotNull(savedEvent);
        assertNotNull(savedEvent.getIdentifier());
        assertEquals(aggregateIdentifier, savedEvent.getAggregateIdentifier());
        assertEquals(EXPECTED_EVENT_TYPE, savedEvent.getEventType());
        assertNotNull(savedEvent.getTransaction());
        assertNotNull(savedEvent.getTransaction().value());
        assertNotNull(savedEvent.getCreatedAt());
    }


}
