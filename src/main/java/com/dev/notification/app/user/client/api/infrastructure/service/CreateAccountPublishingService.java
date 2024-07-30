package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.models.EventCreateAccountPublishing;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventCreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.CreateAccountTopic;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateAccountPublishingService implements PublishingService<EventCreateAccountPublishing> {

    private final ApplicationEventPublisher eventPublisher;
    private final SendEventCreateAccountTopic sendEventCreateAccountTopic;
    private final Gson gson;

    @Override
    public void publish(final EventCreateAccountPublishing dto) {
        eventPublisher.publishEvent(new CreateAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
        sendEventCreateAccountTopic.execute(
                CreateAccountTopic.builder().email(dto.account().getEmail()).password(dto.account().getPassword()).admin(dto.account().isAdmin()).build()
        );
    }
}
