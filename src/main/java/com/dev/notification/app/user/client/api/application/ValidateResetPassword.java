package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidateResetPassword {
    private final HashTokenGateway hashTokenGateway;

    @Value("${spring.data.redis.key.prefix-name.reset-password}")
    private String prefix;

    public void execute(final Account account, final String hash){
        final var hashToken = hashTokenGateway.get(prefix, account.getIdentifier());
        validate(hashToken, hash);
    }

    private void validate(final HashToken hashToken, final String hash) {
        final var currentDate = LocalDateTime.now();
        if (!Objects.equals(hashToken.getHashcode(), hash)) throw new DomainException("This hashcode is not the same as the saved one!");
        if (hashToken.getExpirationDate().isBefore(currentDate)) throw new DomainException("This hash-code is expired!");
    }
}
