package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ConfirmedAccountUseCase {
    private final AccountGateway accountGateway;
    private final NotificationGateway notificationGateway;
    private final SendNotificationUseCase sendNotificationUseCase;
    private final PublishingService<EventPublishing> publishingService;

    @Transactional
    public void execute(final ConfirmedHashDTO dto){
        final var notification = notificationGateway.findByContact(dto.email());
        validate(notification, dto);
        final var account = accountGateway.findAccountByEmailWithThrows(dto.email());
        account.isConfirmedAccount();
        accountGateway.save(account);
        sendNotificationUseCase.execute(account.getEmail(), "welcome", List.of(new Parameter("name", account.getFullName())));
        publishingService.publish(EventPublishing.builder().eventType(EventType.CONFIRMED_ACCOUNT_EVENT).account(account).build());
    }

    private void validate(final Notification notification,
                          final ConfirmedHashDTO dto) {
        // TODO: recuperar do redis o token.
        final var currentDate = LocalDateTime.now();
        final var notificationHash = notification.findByName("hashcode");
        final var expirationDate = notification.findByName("expiration-date");

        if (!Objects.equals(dto.hash(), notificationHash.value()))
            throw new DomainException("This hashcode is not the same as the saved one!");
        if (currentDate.isAfter(DateUtils.convertToString(expirationDate.value())))
            throw new DomainException("This hashcode is expired!");
    }
}
