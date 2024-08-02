package com.dev.notification.app.user.client.api.domain.service;

import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;

public interface ResetPasswordService {
    void confirmed(final ConfirmedHashDTO dto);
}
