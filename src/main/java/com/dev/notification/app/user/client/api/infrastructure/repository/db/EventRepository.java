package com.dev.notification.app.user.client.api.infrastructure.repository.db;

import com.dev.notification.app.user.client.api.infrastructure.repository.db.models.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, String> {
    EventEntity findByAggregateIdentifier(final String identifier);
}
