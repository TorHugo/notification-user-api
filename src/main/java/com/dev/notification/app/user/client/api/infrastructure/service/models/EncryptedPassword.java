package com.dev.notification.app.user.client.api.infrastructure.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public record EncryptedPassword(
        String encryptedPassword,
        @JsonIgnore
        String temporaryPassword
) {
}
