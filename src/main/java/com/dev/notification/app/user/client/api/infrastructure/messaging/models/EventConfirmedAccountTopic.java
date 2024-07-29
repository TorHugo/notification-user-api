package com.dev.notification.app.user.client.api.infrastructure.messaging.models;

import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import lombok.Builder;

import java.util.List;

@Builder
public record EventConfirmedAccountTopic(
        String to,
        String template,
        List<Parameter> parameters
) {
}
