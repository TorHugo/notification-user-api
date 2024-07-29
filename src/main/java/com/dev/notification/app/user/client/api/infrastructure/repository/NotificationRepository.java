package com.dev.notification.app.user.client.api.infrastructure.repository;

import com.dev.notification.app.user.client.api.infrastructure.repository.models.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
}
