package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ConfirmedAccount {
    private final AccountGateway accountGateway;
    private final HashTokenGateway hashTokenGateway;
    private final PublishingService<EventPublishing> publishingService;

    @Value("${spring.data.redis.key.prefix-name.confirmed-account}")
    private String prefix;

    @Transactional
    public void execute(final ConfirmedHashDTO dto){
        final var account = accountGateway.findAccountByEmailWithThrows(dto.email());
        final var hashtoken = hashTokenGateway.get(prefix, account.getIdentifier());
        validate(hashtoken, dto);
        account.isConfirmedAccount();
        accountGateway.save(account);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.CONFIRMED_ACCOUNT_EVENT)
                .account(account)
                .build());
    }

    private void validate(final HashToken hashtoken,
                          final ConfirmedHashDTO dto) {
        final var currentDate = LocalDateTime.now();
        if (!Objects.equals(dto.hash(), hashtoken.getHashcode()))
            throw new DomainException("This hashcode is not the same as the saved one!");
        if (currentDate.isAfter(hashtoken.getExpirationDate()))
            throw new DomainException("This hashcode is expired!");
    }
}
