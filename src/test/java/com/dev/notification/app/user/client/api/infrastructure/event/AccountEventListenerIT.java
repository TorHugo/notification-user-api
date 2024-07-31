package com.dev.notification.app.user.client.api.infrastructure.event;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class AccountEventListenerIT {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private EventGateway eventGateway;

    @Test
    @DisplayName("Should save CreateAccountEvent with successfully.")
    void t1(){
        // Given
        final var expectedAggregateIdentifier = IdentifierUtils.unique();
        final var expectedEventType = EventType.CREATE_ACCOUNT_EVENT;
        final var expectedTransaction = "{\"admin\": false, \"email\": {\"value\": \"test@email.com\"}, \"active\": true, \"lastName\": \"Arruda\", \"password\": \"$2a$10$eJVnNwpfBZtIthMHIRPMf./MQlSg7bkjXsgXOzpB.fTwfOrOmjf52\", \"confirmed\": false, \"createdAt\": \"2024-07-30T11:41:03.9265677\", \"firstName\": \"Victor\", \"identifier\": \"78090050-cf0d-4a98-9b79-c6f04b3780f6\"}";
        final var event = new CreateAccountEvent(this, expectedAggregateIdentifier, expectedTransaction);
        // When
        eventPublisher.publishEvent(event);
        // Then
        final var actualEvent = eventGateway.findByAggregateIdentifier(expectedAggregateIdentifier);
        assertNotNull(actualEvent);
        assertNotNull(actualEvent.getIdentifier());
        assertEquals(expectedAggregateIdentifier, actualEvent.getAggregateIdentifier());
        assertEquals(expectedEventType, actualEvent.getEventType());
        assertEquals(expectedTransaction, actualEvent.getTransaction().value());
        assertNotNull(actualEvent.getCreatedAt());
    }

    @Test
    @DisplayName("Should save ConfirmedAccountEvent with successfully.")
    void t2(){
        // Given
        final var expectedAggregateIdentifier = IdentifierUtils.unique();
        final var expectedEventType = EventType.CONFIRMED_ACCOUNT_EVENT;
        final var expectedTransaction = "{\"admin\": false, \"email\": {\"value\": \"test@email.com\"}, \"active\": true, \"lastName\": \"Arruda\", \"password\": \"$2a$10$eJVnNwpfBZtIthMHIRPMf./MQlSg7bkjXsgXOzpB.fTwfOrOmjf52\", \"confirmed\": true, \"createdAt\": \"2024-07-30T11:41:03.926568\", \"firstName\": \"Victor\", \"identifier\": \"78090050-cf0d-4a98-9b79-c6f04b3780f6\"}";
        final var event = new ConfirmedAccountEvent(this, expectedAggregateIdentifier, expectedTransaction);
        // When
        eventPublisher.publishEvent(event);
        // Then
        final var actualEvent = eventGateway.findByAggregateIdentifier(expectedAggregateIdentifier);
        assertNotNull(actualEvent);
        assertNotNull(actualEvent.getIdentifier());
        assertEquals(expectedAggregateIdentifier, actualEvent.getAggregateIdentifier());
        assertEquals(expectedEventType, actualEvent.getEventType());
        assertEquals(expectedTransaction, actualEvent.getTransaction().value());
        assertNotNull(actualEvent.getCreatedAt());
    }

    @Test
    @DisplayName("Should throws exception (@GatewayException) when event not found.")
    void t3(){
        // Given
        final var expectedMessageError = "Event not found with aggregate identifier.";
        final var expectedAggregateIdentifier = IdentifierUtils.unique();
        // When
        final var exception = assertThrows(GatewayException.class, () -> eventGateway.findByAggregateIdentifier(expectedAggregateIdentifier));
        // Then
        assertNotNull(exception);
        assertEquals(expectedMessageError, exception.getMessage());
    }
}
