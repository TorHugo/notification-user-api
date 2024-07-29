package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.models.PublishingEventCreateAccount;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventCreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.EventConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.EventCreateAccountTopic;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PublishingServiceImpl implements PublishingService {

    private final ApplicationEventPublisher eventPublisher;
    private final SendEventCreateAccountTopic sendEventCreateAccountTopic;
    private final SendEventConfirmedAccountTopic sendEventConfirmedAccountTopic;
    private final Gson gson;

    @Override
    public void publish(final PublishingEventCreateAccount dto) {
        eventPublisher.publishEvent(new CreateAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
        sendEventCreateAccountTopic.execute(EventCreateAccountTopic.builder()
                        .email(dto.account().getEmail())
                        .password(dto.account().getPassword())
                        .admin(dto.account().isAdmin())
                .build());
        sendEventConfirmedAccountTopic.execute(EventConfirmedAccountTopic.builder()
                        .to(dto.notification().getTo())
                        .template(dto.notification().getTemplate())
                        .parameters(dto.notification().getParameters())
                .build());
    }
}
