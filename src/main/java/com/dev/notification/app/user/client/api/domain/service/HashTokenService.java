package com.dev.notification.app.user.client.api.domain.service;

public interface HashTokenService {
    void resendConfirmedHashToken(final String email);
}
