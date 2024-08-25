package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.SendNotification;
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

import static com.dev.notification.app.user.client.api.domain.enums.TemplateMessage.CONFIRMED_ACCOUNT;
import static com.dev.notification.app.user.client.api.domain.enums.TemplateMessage.WELCOME_ACCOUNT;

@Service
@RequiredArgsConstructor
public class PublishingServiceImpl implements PublishingService<EventPublishing> {

    private final Gson gson;
    private final ApplicationEventPublisher eventPublisher;
    private final SendEventCreateAccountTopic sendEventCreateAccountTopic;
    private final SendEventConfirmedAccountTopic sendEventConfirmedAccountTopic;
    private final SendEventRestPasswordTopic sendEventRestPasswordTopic;
    private final SendNotification sendNotification;

    @SuppressWarnings("unchecked")
    @Override
    public void publish(final EventPublishing dto) {
        switch (dto.eventType()){
            case CREATE_ACCOUNT_EVENT:
                final var parameters = (List<Parameter>) dto.object();
                sendNotification.execute(dto.account().getEmail(), CONFIRMED_ACCOUNT.getSubject(),CONFIRMED_ACCOUNT.getTemplate(), parameters);
                sendEventCreateAccountTopic.execute(
                        CreateAccountTopic.builder().email(dto.account().getEmail()).password(dto.account().getPassword()).admin(dto.account().isAdmin()).build()
                );
                eventPublisher.publishEvent(new CreateAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                break;
            case CONFIRMED_ACCOUNT_EVENT:
                sendNotification.execute(dto.account().getEmail(), WELCOME_ACCOUNT.getSubject(),WELCOME_ACCOUNT.getTemplate(), List.of(new Parameter("name", dto.account().getFullName())));
                sendEventConfirmedAccountTopic.execute(
                        ConfirmedAccountTopic.builder().email(dto.account().getEmail()).confirmed(dto.account().isConfirmed()).build()
                );
                eventPublisher.publishEvent(new ConfirmedAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                break;
            case RESEND_CONFIRMATION_EVENT:
                final var resendParameters = (List<Parameter>) dto.object();
                sendNotification.execute(dto.account().getEmail(), CONFIRMED_ACCOUNT.getSubject(),CONFIRMED_ACCOUNT.getTemplate(), resendParameters);
                eventPublisher.publishEvent(new ResendConfirmedAccountEvent(this, dto.account().getIdentifier(), gson.toJson(dto.account())));
                break;
            case SEND_RESET_PASSWORD_EVENT:
                final var resetPassword = (HashToken) dto.object();
                sendNotification.execute(dto.account().getEmail(), "testing","send-hash-reset-password", List.of(new Parameter("hash-code", resetPassword.getHashcode())));
                eventPublisher.publishEvent(new SendResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(dto.object())));
                break;
            case CONFIRMED_RESET_PASSWORD_EVENT:
                final var confirmedEvent = (EncryptedPassword) dto.object();
                sendNotification.execute(dto.account().getEmail(), "testing","temporary-password", List.of(new Parameter("temporary-password", confirmedEvent.temporaryPassword())));
                sendEventRestPasswordTopic.execute(
                        RedefinitionPasswordTopic.builder().email(dto.account().getEmail()).password(confirmedEvent.encryptedPassword()).build()
                );
                eventPublisher.publishEvent(new ConfirmedResetPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(confirmedEvent.encryptedPassword())));
                break;
            case REDEFINITION_PASSWORD_EVENT:
                final var redefinitionEvent = (EncryptedPassword) dto.object();
                sendEventRestPasswordTopic.execute(
                        RedefinitionPasswordTopic.builder().email(dto.account().getEmail()).password(redefinitionEvent.encryptedPassword()).build()
                );
                eventPublisher.publishEvent(new RedefinitionPasswordEvent(this, dto.account().getIdentifier(), gson.toJson(redefinitionEvent)));
                break;
        }
    }
}
