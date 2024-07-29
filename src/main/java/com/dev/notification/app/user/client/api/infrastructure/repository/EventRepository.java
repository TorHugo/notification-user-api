package com.dev.notification.app.user.client.api.infrastructure.repository;

import com.dev.notification.app.user.client.api.infrastructure.repository.models.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, String> {
}
