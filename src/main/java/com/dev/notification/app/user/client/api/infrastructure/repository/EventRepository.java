package com.dev.notification.app.user.client.api.infrastructure.repository;

import com.dev.notification.app.user.client.api.infrastructure.repository.models.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<EventEntity, String> {
    EventEntity findByAggregateIdentifier(final String identifier);
}
