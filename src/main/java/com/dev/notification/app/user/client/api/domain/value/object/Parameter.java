package com.dev.notification.app.user.client.api.domain.value.object;

import lombok.Builder;

@Builder
public record Parameter(
        String name,
        String value
) {
}
