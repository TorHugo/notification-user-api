package com.dev.notification.app.user.client.api.domain.service;

public interface EncryptionService {
    String encryption(final String value);
    boolean matches(final String expectedValue, final String hashed);
}
