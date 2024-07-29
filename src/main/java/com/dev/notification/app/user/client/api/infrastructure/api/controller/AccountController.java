package com.dev.notification.app.user.client.api.infrastructure.api.controller;

import com.dev.notification.app.user.client.api.application.CreateAccountUseCase;
import com.dev.notification.app.user.client.api.infrastructure.api.AccountAPI;
import com.dev.notification.app.user.client.api.infrastructure.api.models.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountAPI {
    private final CreateAccountUseCase createAccountUseCase;

    @Override
    public String create(final CreateAccountDTO request) {
        return createAccountUseCase.execute(request).getIdentifier();
    }
}
