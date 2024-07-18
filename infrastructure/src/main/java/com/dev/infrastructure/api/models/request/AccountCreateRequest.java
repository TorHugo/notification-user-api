package com.dev.infrastructure.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountCreateRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String password
) {

}
