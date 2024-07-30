package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Notification;

public interface NotificationGateway {
    Notification save(final Notification notification);
    Notification findByAccount(final String contact);
}
