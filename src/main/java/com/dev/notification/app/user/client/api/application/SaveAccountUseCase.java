package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAccountUseCase {
    private final AccountGateway accountGateway;

    public Account execute(final Account account){
        return accountGateway.saveToAccount(account);
    }
}
