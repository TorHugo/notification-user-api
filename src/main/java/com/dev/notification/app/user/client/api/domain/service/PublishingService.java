package com.dev.notification.app.user.client.api.domain.service;

import com.dev.notification.app.user.client.api.domain.service.models.PublishingEventCreateAccount;

public interface PublishingService {
    void publish(final PublishingEventCreateAccount dto);
}
