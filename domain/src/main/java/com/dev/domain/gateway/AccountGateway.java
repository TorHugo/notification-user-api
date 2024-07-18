package com.dev.domain.gateway;

import com.dev.domain.entity.Account;

public interface AccountGateway {
    void saveToAccount(final Account account);
    Account findAccountByIdentifier(final String identifier);
    Account findAccountByEmailOrNull(final String email);
    Account inactivateAccountByIdentifier(final String identifier);
    Account activateAccountByIdentifier(final String identifier);
    Account confirmateAccountByIdentifier(final String identifier);
    Account updateAccount(final Account account);
}
