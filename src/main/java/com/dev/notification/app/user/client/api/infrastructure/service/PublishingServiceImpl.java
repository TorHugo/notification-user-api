package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.CreateNotification;
import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.event.models.*;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventCreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.SendEventRestPasswordTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.ConfirmedAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.CreateAccountTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.RedefinitionPasswordTopic;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EncryptedPassword;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublishingServiceImpl implements PublishingService<EventPublishing> {

    private final Gson gson;
    private final ApplicationEventPublisher eventPublisher;
    private final SendEventCreateAccountTopic sendEventCreateAccountTopic;
    private final SendEventConfirmedAccountTopic sendEventConfirmedAccountTopic;
    private final SendEventRestPasswordTopic sendEventRestPasswordTopic;
    private final CreateNotification createNotification;

    @Override
    public void publish(final EventPublishing dto) {
        switch (dto.eventType()){
            case CREATE_ACCOUNT_EVENT:
                final var parameters = (List<Parameter>) dto.object();
                eventPublisher.publishEvent(new CreateAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                createNotification.execute(dto.account().getEmail(), "hashcode-confirmed-account", parameters);
                sendEventCreateAccountTopic.execute(
                        CreateAccountTopic.builder().email(dto.account().getEmail()).password(dto.account().getPassword()).admin(dto.account().isAdmin()).build()
                );
                break;
            case CONFIRMED_ACCOUNT_EVENT:
                eventPublisher.publishEvent(new ConfirmedAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                createNotification.execute(dto.account().getEmail(), "welcome", List.of(new Parameter("name", dto.account().getFullName())));
                sendEventConfirmedAccountTopic.execute(
                        ConfirmedAccountTopic.builder().email(dto.account().getEmail()).confirmed(dto.account().isConfirmed()).build()
                );
                break;
            case SEND_RESET_PASSWORD_EVENT:
                final var resetPassword = (HashToken) dto.object();
                eventPublisher.publishEvent(new SendResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(dto.object())));
                createNotification.execute(dto.account().getEmail(), "send-hash-reset-password", List.of(new Parameter("hash-code", resetPassword.getHashcode())));
                break;
            case CONFIRMED_RESET_PASSWORD_EVENT:
                final var confirmedEvent = (EncryptedPassword) dto.object();
                eventPublisher.publishEvent(new ConfirmedResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(confirmedEvent)));
                sendEventRestPasswordTopic.execute(
                        RedefinitionPasswordTopic.builder().email(dto.account().getEmail()).password(confirmedEvent.encryptedPassword()).build()
                );
                break;
            case REDEFINITION_PASSWORD_EVENT:
                final var redefinitionEvent = (EncryptedPassword) dto.object();
                eventPublisher.publishEvent(new RedefinitionPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(redefinitionEvent)));
                sendEventRestPasswordTopic.execute(
                        RedefinitionPasswordTopic.builder().email(dto.account().getEmail()).password(redefinitionEvent.encryptedPassword()).build()
                );
                break;
        }
    }
}
