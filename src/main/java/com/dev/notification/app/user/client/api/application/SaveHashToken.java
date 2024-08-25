package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveHashToken {
    private final HashTokenGateway hashTokenGateway;

    public HashToken execute(final String prefix,
                             final String identifier,
                             final Integer digits,
                             final Integer milliseconds){
        final var hashToken = HashToken.create(
                prefix,
                identifier,
                HashCodeUtils.create(digits),
                DateUtils.fromMillis(milliseconds)
        );
        hashTokenGateway.save(hashToken);
        return hashToken;
    }
}
