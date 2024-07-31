package com.dev.notification.app.user.client.api.infrastructure.event;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.exception.template.EventException;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.infrastructure.event.AccountEventListener;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@UnitaryTest
class AccountEventListenerTest {

    @InjectMocks
    private AccountEventListener accountEventListener;

    @Mock
    private EventGateway eventGateway;

    @Test
    @DisplayName("Should throw EventException when saving CreateAccountEvent fails.")
    void t1() {
        // Given
        final var expectedErrorMessage = "Error creating event!";
        final var expectedEvent = new CreateAccountEvent(this, "value", "value");

        // When
        doThrow(new RuntimeException("Simulated exception")).when(eventGateway).save(any());
        final var exception = assertThrows(EventException.class, () -> accountEventListener.handlerCreateAccount(expectedEvent));
        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw EventException when saving ConfirmedAccountEvent fails.")
    void t2() {
        // Given
        final var expectedErrorMessage = "Error creating event!";
        final var expectedEvent = new ConfirmedAccountEvent(this, "value", "value");

        // When
        doThrow(new RuntimeException("Simulated exception")).when(eventGateway).save(any());
        final var exception = assertThrows(EventException.class, () -> accountEventListener.handlerConfirmedAccount(expectedEvent));
        // Then
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
