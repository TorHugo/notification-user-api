package com.dev.notification.app.user.client.api.infrastructure.api;

import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.EmailAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.RedefinitionPasswordDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.response.AccountSuccessfullyDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.response.MessageDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/account")
public interface AccountAPI {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    AccountSuccessfullyDTO create(final @Valid @RequestBody CreateAccountDTO request);

    @PostMapping("/confirmed")
    @ResponseStatus(HttpStatus.CREATED)
    MessageDTO confirmed(final @Valid @RequestBody ConfirmedHashDTO request);

    @PostMapping("/reset-password/send-hash")
    @ResponseStatus(HttpStatus.CREATED)
    MessageDTO forgetPassword(final @Valid @RequestBody EmailAccountDTO request);

    @PostMapping("/reset-password/confirmed")
    @ResponseStatus(HttpStatus.CREATED)
    MessageDTO forgetPassword(final @Valid @RequestBody ConfirmedHashDTO request);

    @PutMapping("/redefinition-password")
    @ResponseStatus(HttpStatus.CREATED)
    MessageDTO redefinitionPassword(final @Valid @RequestBody RedefinitionPasswordDTO request);
}
