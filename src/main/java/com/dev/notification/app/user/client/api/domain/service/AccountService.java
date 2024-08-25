package com.dev.notification.app.user.client.api.domain.service;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;

public interface AccountService {
    Account create(final CreateAccountDTO dto);
    void confirmed(final ConfirmedHashDTO dto);
}
