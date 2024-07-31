package com.dev.notification.app.user.client.api.domain;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@UnitaryTest
class EventDomainTest {
    private static final String AGGREGATE_IDENTIFIER = IdentifierUtils.unique();
    private static final EventType EVENT_TYPE = EventType.CONFIRMED_ACCOUNT_EVENT;
    private static final String TRANSACTION = "transaction";

    @Test
    @DisplayName("Should be instantiated Event with success.")
    void t1(){
        // Given && When
        final var event = Event.create(AGGREGATE_IDENTIFIER, EVENT_TYPE, TRANSACTION);
        // Then
        assertNotNull(event);
        assertNotNull(event.getIdentifier());
        assertEquals(AGGREGATE_IDENTIFIER, event.getAggregateIdentifier());
        assertEquals(EVENT_TYPE, event.getEventType());
        assertEquals(TRANSACTION, event.getTransaction().value());
        assertNotNull(event.getCreatedAt());
    }

    @Test
    @DisplayName("Should be restore Event with success.")
    void t2(){
        // Given
        final var expectedIdentifier = IdentifierUtils.unique();
        final var expectedCurrentDate = LocalDateTime.now();
        // When
        final var event = Event.restore(expectedIdentifier, AGGREGATE_IDENTIFIER, EVENT_TYPE, TRANSACTION, expectedCurrentDate);
        // Then
        assertNotNull(event);
        assertEquals(expectedIdentifier, event.getIdentifier());
        assertEquals(AGGREGATE_IDENTIFIER, event.getAggregateIdentifier());
        assertEquals(EVENT_TYPE, event.getEventType());
        assertEquals(TRANSACTION, event.getTransaction().value());
        assertEquals(expectedCurrentDate, event.getCreatedAt());
    }

    @Test
    @DisplayName("Should be throws exception when aggregate identifier is empty.")
    void t3(){
        // Given
        final var expectedMessageError = "This aggregate identifier must be not null or empty!";
        // When
        final var event = assertThrows(DomainException.class, ()-> Event.create("", EVENT_TYPE, TRANSACTION));
        // Then
        assertNotNull(event);
        assertEquals(expectedMessageError, event.getMessage());
    }

    @Test
    @DisplayName("Should be throws exception when aggregate identifier is null.")
    void t4(){
        // Given
        final var expectedMessageError = "This aggregate identifier must be not null or empty!";
        // When
        final var event = assertThrows(DomainException.class, ()-> Event.create(null, EVENT_TYPE, TRANSACTION));
        // Then
        assertNotNull(event);
        assertEquals(expectedMessageError, event.getMessage());
    }
}
