package com.dev.infrastructure.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountCreateResponse(
        @JsonProperty("account_id") String account
) {
}
