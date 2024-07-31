package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Notification;

public interface NotificationGateway {
    void save(final Notification notification);
    Notification findByContact(final String contact);
}
