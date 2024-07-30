package com.dev.notification.app.user.client.api.domain.service;

public interface PublishingService<T> {
    void publish(final T dto);
}
