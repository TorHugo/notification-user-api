package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.ForgetPassword;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.ForgetPasswordGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidateResetPassword {
    private final ForgetPasswordGateway forgetPasswordGateway;

    public void execute(final Account account, final String hash){
        final var forgetPassword = forgetPasswordGateway.findByAggregateIdentifier(account.getIdentifier());
        validate(forgetPassword, hash);
    }

    private void validate(final ForgetPassword forgetPassword, final String hash) {
        final var currentDate = LocalDateTime.now();
        if (!Objects.equals(forgetPassword.getHashCode(), hash)) throw new DomainException("This hashcode is not the same as the saved one!");
        if (forgetPassword.getExpirationDate().isBefore(currentDate)) throw new DomainException("This hash-code is expired!");
    }
}
