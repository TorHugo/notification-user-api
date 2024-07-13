package com.dev.domain.gateway;

import com.dev.domain.entity.Account;

public interface AccountGateway {
    Account saveToAccount(final Account account);
    Account findAccountByIdentifier(final String identifier);
    Account inactivateAccountByIdentifier(final String identifier);
    Account activateAccountByIdentifier(final String identifier);
    Account updateAccount(final Account account);
}
