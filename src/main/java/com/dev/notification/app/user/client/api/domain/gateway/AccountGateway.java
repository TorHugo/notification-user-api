package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Account;

public interface AccountGateway {
    Account saveToAccount(final Account account);
    Account findAccountByEmail(final String email);
    Account findAccountByEmailWithThrows(final String email);
}
