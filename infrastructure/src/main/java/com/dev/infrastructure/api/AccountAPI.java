package com.dev.infrastructure.api;

import com.dev.infrastructure.api.models.request.AccountCreateRequest;
import com.dev.infrastructure.api.models.response.AccountCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/account")
public interface AccountAPI {
    @PostMapping("/public/create")
    @ResponseStatus(HttpStatus.CREATED)
    AccountCreateResponse createAccount(final @RequestBody AccountCreateRequest request);
}
