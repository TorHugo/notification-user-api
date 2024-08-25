package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountWithThrows {
    private final AccountGateway accountGateway;

    public Account execute(final String email){
        return accountGateway.findAccountByEmailWithThrows(email);
    }
}
