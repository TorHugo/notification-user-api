package com.dev.notification.app.user.client.api.infrastructure.messaging.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventConfirmedAccountTopic(
        String email,
        String hashConfirmed,
        boolean confirmed,
        @JsonProperty("expiration_date") LocalDateTime expirationDate
) {
}
