package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedResetPasswordEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.SendResetPasswordEvent;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventCreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventRestPasswordTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.ConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.CreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.ResetPasswordTopic;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EncryptedPassword;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishingServiceImpl implements PublishingService<EventPublishing> {

    private final Gson gson;
    private final ApplicationEventPublisher eventPublisher;
    private final SendEventCreateAccountTopic sendEventCreateAccountTopic;
    private final SendEventConfirmedAccountTopic sendEventConfirmedAccountTopic;
    private final SendEventRestPasswordTopic sendEventRestPasswordTopic;

    @Override
    public void publish(final EventPublishing dto) {
        switch (dto.eventType()){
            case CREATE_ACCOUNT_EVENT:
                eventPublisher.publishEvent(new CreateAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                sendEventCreateAccountTopic.execute(
                        CreateAccountTopic.builder().email(dto.account().getEmail()).password(dto.account().getPassword()).admin(dto.account().isAdmin()).build()
                );
                break;
            case CONFIRMED_ACCOUNT_EVENT:
                eventPublisher.publishEvent(new ConfirmedAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                sendEventConfirmedAccountTopic.execute(
                        ConfirmedAccountTopic.builder().email(dto.account().getEmail()).confirmed(dto.account().isConfirmed()).build()
                );
                break;
            case SEND_RESET_PASSWORD_EVENT:
                eventPublisher.publishEvent(new SendResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(dto.object())));
                break;
            case CONFIRMED_RESET_PASSWORD_EVENT:
                final var event = (EncryptedPassword) dto.object();
                eventPublisher.publishEvent(new ConfirmedResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(event)));
                sendEventRestPasswordTopic.execute(
                        ResetPasswordTopic.builder().password(event.encryptedPassword()).build()
                );
        }
    }
}
