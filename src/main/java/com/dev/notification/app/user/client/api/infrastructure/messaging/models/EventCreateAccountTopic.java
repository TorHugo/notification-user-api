package com.dev.notification.app.user.client.api.infrastructure.messaging.models;

import lombok.Builder;

@Builder
public record EventCreateAccountTopic(
        String email,
        String password,
        boolean admin
) {
}
