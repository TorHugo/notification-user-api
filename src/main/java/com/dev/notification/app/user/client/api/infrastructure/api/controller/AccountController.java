package com.dev.notification.app.user.client.api.infrastructure.api.controller;

import com.dev.notification.app.user.client.api.application.ConfirmedAccountUseCase;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.infrastructure.api.AccountAPI;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.response.AccountSuccessfullyDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.response.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountAPI {
    private final AccountService createAccountUseCase;
    private final ConfirmedAccountUseCase confirmedAccountUseCase;

    @Override
    public AccountSuccessfullyDTO create(final CreateAccountDTO request) {
        final var account = createAccountUseCase.create(request);
        return new AccountSuccessfullyDTO(account.getIdentifier());
    }

    @Override
    public MessageDTO confirmed(final ConfirmedAccountDTO request) {
        confirmedAccountUseCase.execute(request);
        return new MessageDTO("Account is confirmed with successfully!");
    }
}
