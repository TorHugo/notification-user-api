package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.models.EventConfirmedAccountPublishing;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventNotificationTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.ConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.NotificationTopic;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConfirmedAccountPublishingService implements PublishingService<EventConfirmedAccountPublishing> {

    private final ApplicationEventPublisher eventPublisher;
    private final SendEventConfirmedAccountTopic sendEventConfirmedAccountTopic;
    private final SendEventNotificationTopic sendEventNotificationTopic;
    private final Gson gson;

    @Override
    public void publish(final EventConfirmedAccountPublishing dto) {
        eventPublisher.publishEvent(new ConfirmedAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
        sendEventConfirmedAccountTopic.execute(ConfirmedAccountTopic.builder()
                        .email(dto.account().getEmail())
                        .confirmed(dto.account().isConfirmed())
                .build());
        sendEventNotificationTopic.execute(NotificationTopic.builder()
                        .to(dto.notification().getContact())
                        .template(dto.notification().getTemplate())
                        .parameters(dto.notification().getParameters())
                .build());
    }
}
