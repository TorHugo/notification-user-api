package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveNotificationUseCase {
    private final NotificationGateway notificationGateway;

    public Notification execute(final Notification notification){
        return notificationGateway.save(notification);
    }
}
