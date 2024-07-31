package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class AccountServiceIT {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private NotificationGateway notificationGateway;

    @Autowired
    private EventGateway eventGateway;

    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String EMAIL = "email@example.com";
    private static final String PASSWORD = "Password@123";

    private static final String EXPECTED_TEMPLATE = "hashcode-confirmed-account";
    private static final EventType EXPECTED_EVENT_TYPE = EventType.CREATE_ACCOUNT_EVENT;

    @Test
    @DisplayName("Should create account with success.")
    void t1(){
        // Given && When
        final var account = accountService.create(new CreateAccountDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD));
        assertNotNull(account);
        final var savedAccount = accountGateway.findAccountByIdentifier(account.getIdentifier());
        final var savedNotification = notificationGateway.findByContact(savedAccount.getEmail());
        final var savedEvent = eventGateway.findByAggregateIdentifier(savedAccount.getIdentifier());

        // Then
        assertAccount(savedAccount);
        assertNotification(savedNotification);
        assertEvent(savedEvent, savedAccount.getIdentifier());
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
        assertEquals(EXPECTED_TEMPLATE, savedNotification.getTemplate());
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
