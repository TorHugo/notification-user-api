package com.dev.notification.app.user.client.api.infrastructure.messaging.models;

import lombok.Builder;

@Builder
public record ResetPasswordTopic(
        String password
) {
}
