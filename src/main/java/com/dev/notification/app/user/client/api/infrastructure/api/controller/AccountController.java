package com.dev.notification.app.user.client.api.infrastructure.api.controller;

import com.dev.notification.app.user.client.api.application.ConfirmedAccountUseCase;
import com.dev.notification.app.user.client.api.application.SendResetPasswordUseCase;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.domain.service.ResetPasswordService;
import com.dev.notification.app.user.client.api.infrastructure.api.AccountAPI;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.EmailAccountDTO;
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
    private final SendResetPasswordUseCase sendResetPasswordUseCase;
    private final ResetPasswordService resetPasswordService;

    @Override
    public AccountSuccessfullyDTO create(final CreateAccountDTO request) {
        final var account = createAccountUseCase.create(request);
        return new AccountSuccessfullyDTO(account.getIdentifier());
    }

    @Override
    public MessageDTO confirmed(final ConfirmedHashDTO request) {
        confirmedAccountUseCase.execute(request);
        return new MessageDTO("Account is confirmed with successfully!");
    }

    @Override
    public MessageDTO forgetPassword(final EmailAccountDTO request) {
        sendResetPasswordUseCase.execute(request.email());
        return new MessageDTO("Please check your email as a verification code has been sent to it.");
    }

    @Override
    public MessageDTO forgetPassword(final ConfirmedHashDTO request) {
        resetPasswordService.confirmed(request);
        return new MessageDTO("Please check your email, a temporary password with an expiration date of 15 minutes has been sent so you can reset it.");
    }
}
