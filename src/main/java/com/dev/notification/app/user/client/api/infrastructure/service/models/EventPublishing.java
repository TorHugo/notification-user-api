package com.dev.notification.app.user.client.api.infrastructure.service.models;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import lombok.Builder;

@Builder
public record EventPublishing(
        EventType eventType,
        Account account,
        Object object
) {
}
