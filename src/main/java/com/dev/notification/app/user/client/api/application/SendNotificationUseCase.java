package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventNotificationTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.NotificationTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SendNotificationUseCase {
    private final NotificationGateway notificationGateway;
    private final SendEventNotificationTopic sendEventNotificationTopic;

    public void execute(final String contact, final String template, final List<Parameter> parameters){
        sendEventNotificationTopic.execute(NotificationTopic.builder()
                .to(contact)
                .template(template)
                .parameters(parameters)
                .build());
        final var notification = Notification.create(contact, template, parameters);
        notificationGateway.save(notification);
    }
}
