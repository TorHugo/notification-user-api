package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Transaction;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Event {

    private final String identifier;
    private final String aggregateIdentifier;
    private final EventType eventType;
    private final Transaction transaction;
    private final LocalDateTime createdAt;

    private Event(final String identifier,
                  final String aggregateIdentifier,
                  final EventType eventType,
                  final String transaction,
                  final LocalDateTime createdAt){
        this.identifier = identifier;
        this.aggregateIdentifier = aggregateIdentifier;
        this.eventType = eventType;
        this.transaction = new Transaction(transaction);
        this.createdAt = createdAt;
    }

    public static Event create(
            final String aggregateIdentifier,
            final EventType eventType,
            final String transaction
    ){
        if (Objects.isNull(aggregateIdentifier) || aggregateIdentifier.isEmpty()) throw new DomainException("This aggregate identifier must be not null or empty!");
        return new Event(IdentifierUtils.unique(), aggregateIdentifier, eventType, transaction, LocalDateTime.now());
    }

    public static Event restore(final String identifier, final String aggregateIdentifier, final EventType eventType, final String transaction, final LocalDateTime createdAt){
        return new Event(identifier, aggregateIdentifier, eventType, transaction, createdAt);
    }
}