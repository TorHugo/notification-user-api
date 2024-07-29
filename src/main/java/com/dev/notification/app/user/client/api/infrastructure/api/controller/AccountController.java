package com.dev.notification.app.user.client.api.infrastructure.api.controller;

import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.infrastructure.api.AccountAPI;
import com.dev.notification.app.user.client.api.infrastructure.api.models.AccountSuccessfullyDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountAPI {
    private final AccountService createAccountUseCase;

    @Override
    public AccountSuccessfullyDTO create(final CreateAccountDTO request) {
        final var account = createAccountUseCase.create(request);
        return new AccountSuccessfullyDTO(account.getIdentifier());
    }
}
