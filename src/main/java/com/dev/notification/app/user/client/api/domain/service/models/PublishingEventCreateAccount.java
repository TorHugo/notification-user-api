package com.dev.notification.app.user.client.api.domain.service.models;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import lombok.Builder;

@Builder
public record PublishingEventCreateAccount(
        Account account,
        Notification notification
) {
}
