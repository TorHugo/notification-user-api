package com.dev.notification.app.user.client.api.infrastructure.service.models;

import lombok.Builder;

@Builder
public record EncryptedPassword(
        String encryptedPassword
) {
}
