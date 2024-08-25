package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteHashToken {
    private final HashTokenGateway hashTokenGateway;

    public void execute(final String prefix, final String key){
        hashTokenGateway.delete(prefix, key);
    }
}
