package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindHashToken {
    private final HashTokenGateway hashTokenGateway;

    public HashToken execute(final String prefix,
                             final String identifier){
        return hashTokenGateway.get(prefix, identifier);
    }
}
