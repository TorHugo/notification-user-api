package com.dev.notification.app.user.client.api.domain.value.object;

public interface ValueObject<T> {
    void validateValue(final T value);
    T value();
}
