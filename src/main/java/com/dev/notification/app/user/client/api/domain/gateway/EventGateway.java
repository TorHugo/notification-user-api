package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Event;

public interface EventGateway {
    void save(final Event event);
}
