package com.dev.notification.app.user.client.api.infrastructure.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountSuccessfulyDTO(
        @JsonProperty("account_id") String accountId
) {
}
