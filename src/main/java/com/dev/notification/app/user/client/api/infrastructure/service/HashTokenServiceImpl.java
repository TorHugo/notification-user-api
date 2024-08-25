package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.SaveHashToken;
import com.dev.notification.app.user.client.api.application.DeleteHashToken;
import com.dev.notification.app.user.client.api.application.FindAccountWithThrows;
import com.dev.notification.app.user.client.api.application.FindHashToken;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.service.HashTokenService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HashTokenServiceImpl implements HashTokenService {
    private final FindAccountWithThrows findAccountWithThrows;
    private final SaveHashToken saveHashToken;
    private final FindHashToken findHashToken;
    private final DeleteHashToken deleteHashToken;
    private final PublishingService<EventPublishing> publishingService;

    @Value("${spring.integration.hash.digits.confirmed-account}")
    private Integer digits;
    @Value("${spring.integration.expiration-time.confirmed-account}")
    private Integer milliseconds;
    @Value("${spring.data.redis.key.prefix-name.confirmed-account}")
    private String prefix;

    @Override
    public void resendConfirmedHashToken(final String email) {
        final var account = findAccountWithThrows.execute(email);
        account.isValidConfirmedAccount();
        final var hashToken = findHashToken.execute(prefix, account.getIdentifier());
        if (Objects.nonNull(hashToken))
            deleteHashToken.execute(prefix, account.getIdentifier());
        final var newHashToken = saveHashToken.execute(prefix, account.getIdentifier(), digits, milliseconds);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.RESEND_CONFIRMATION_EVENT)
                .account(account)
                .object(Arrays.asList(
                        new Parameter("name", account.getFullName()),
                        new Parameter("hashcode", newHashToken.getHashcode()),
                        new Parameter("expiration-date", newHashToken.getTime())
                ))
                .build());
    }
}
