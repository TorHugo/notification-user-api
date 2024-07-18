package com.dev.infrastructure.api.controllers;

import com.dev.infrastructure.api.AccountAPI;
import com.dev.infrastructure.api.models.request.AccountCreateRequest;
import com.dev.infrastructure.api.models.response.AccountCreateResponse;
import com.dev.infrastructure.usecase.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController implements AccountAPI {
    private final CreateAccountUseCase createAccountUseCase;

    @Override
    public AccountCreateResponse createAccount(final AccountCreateRequest request) {
        final var result = createAccountUseCase.execute(request);
        return new AccountCreateResponse(result);
    }
}
