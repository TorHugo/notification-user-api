package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPassword {
    private final AccountGateway accountGateway;
    private final HashTokenGateway hashTokenGateway;
    private final PublishingService<EventPublishing> publishingService;

    @Value("${spring.integration.hash.digits.forget-password}")
    private Integer hashDigits;

    @Value("${spring.integration.expiration-time.reset-password}")
    private Integer milliseconds;

    @Value("${spring.data.redis.key.prefix-name.reset-password}")
    private String prefix;

    public void execute(final String email){
        final var account = accountGateway.findAccountByEmailWithThrows(email);
        final var hashToken = HashToken.create(
                prefix,
                account.getIdentifier(),
                HashCodeUtils.create(hashDigits),
                DateUtils.fromMillis(milliseconds)
        );
        hashTokenGateway.save(hashToken);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.SEND_RESET_PASSWORD_EVENT)
                .account(account)
                .object(hashToken).build());
    }
}
