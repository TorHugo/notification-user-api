package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.AccountRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountGatewayImpl implements AccountGateway {
    private final AccountRepository accountRepository;
    private final AccountMapper accountRepositoryMapper;

    @Override
    public Account saveToAccount(final Account account) {
        final var entity = accountRepositoryMapper.fromAggregate(account);
        return accountRepositoryMapper.toAggregate(accountRepository.save(entity));
    }

    @Override
    public Account findAccountByEmail(final String email) {
        final var entity = accountRepository.findByEmail(email);
        return Objects.isNull(entity) ? null : accountRepositoryMapper.toAggregate(entity);
    }

    @Override
    public Account findAccountByEmailWithThrows(final String email) {
        final var entity = accountRepository.findByEmail(email);
        if (Objects.isNull(entity)) throw new GatewayException("Account not found!", email);
        return accountRepositoryMapper.toAggregate(entity);
    }
}
