package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;

public interface HashTokenGateway {
    void save(final HashToken domain);
    HashToken get(final String prefix, final String key);
    void delete(final String prefix, final String key);
}
