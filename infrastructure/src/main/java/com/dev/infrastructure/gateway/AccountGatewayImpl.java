package com.dev.infrastructure.gateway;

import com.dev.domain.entity.Account;
import com.dev.domain.gateway.AccountGateway;
import com.dev.infrastructure.repository.AccountJpaRepository;
import com.dev.infrastructure.repository.mappers.AccountEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountGatewayImpl implements AccountGateway {
    private final AccountJpaRepository accountJpaRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    public void saveToAccount(final Account account) {
        final var entity = accountEntityMapper.fromAggregate(account);
        accountJpaRepository.save(entity);
    }

    @Override
    public Account findAccountByIdentifier(String identifier) {
        return null;
    }

    @Override
    public Account findAccountByEmailOrNull(final String email) {
        final var entity = accountJpaRepository.findByEmail(email);
        return Objects.isNull(entity) ? null : accountEntityMapper.toAggregate(entity);
    }

    @Override
    public Account inactivateAccountByIdentifier(String identifier) {
        return null;
    }

    @Override
    public Account activateAccountByIdentifier(String identifier) {
        return null;
    }

    @Override
    public Account confirmateAccountByIdentifier(String identifier) {
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }
}
