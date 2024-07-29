package com.dev.notification.app.user.client.api.infrastructure.api.models;

import com.dev.notification.app.user.client.api.domain.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CreateAccountDTO(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String email,
        @Password String password
) {
}
