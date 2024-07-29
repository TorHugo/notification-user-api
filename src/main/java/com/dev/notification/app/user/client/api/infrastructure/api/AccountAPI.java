package com.dev.notification.app.user.client.api.infrastructure.api;

import com.dev.notification.app.user.client.api.infrastructure.api.models.CreateAccountDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/account")
public interface AccountAPI {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    String create(final @Valid @RequestBody CreateAccountDTO request);
}
