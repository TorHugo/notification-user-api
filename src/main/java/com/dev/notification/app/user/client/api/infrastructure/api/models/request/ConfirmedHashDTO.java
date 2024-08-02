package com.dev.notification.app.user.client.api.infrastructure.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ConfirmedHashDTO(
        @JsonProperty("hash-code") @NotBlank String hash,
        @JsonProperty("email") @NotBlank String email
) {
}
