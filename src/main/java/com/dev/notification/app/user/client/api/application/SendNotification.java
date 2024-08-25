package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventNotificationTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.NotificationTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SendNotification {
    private final NotificationGateway notificationGateway;
    private final SendEventNotificationTopic sendEventNotificationTopic;

    @Transactional
    public void execute(final String contact,
                        final String subject,
                        final String template,
                        final List<Parameter> parameters){
        sendEventNotificationTopic.execute(NotificationTopic.builder()
                .contact(contact)
                .subject(subject)
                .template(template)
                .parameters(parameters)
                .build());
        final var notification = Notification.create(contact, subject, template, parameters);
        notificationGateway.save(notification);
    }
}
