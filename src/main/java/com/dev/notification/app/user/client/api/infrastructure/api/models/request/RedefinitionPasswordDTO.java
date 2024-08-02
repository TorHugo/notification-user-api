package com.dev.notification.app.user.client.api.infrastructure.api.models.request;

import com.dev.notification.app.user.client.api.domain.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RedefinitionPasswordDTO(
        @JsonProperty("email") String email,
        @JsonProperty("old_password") String oldPassword,
        @Password @JsonProperty("new_password") String newPassword
) {
}
