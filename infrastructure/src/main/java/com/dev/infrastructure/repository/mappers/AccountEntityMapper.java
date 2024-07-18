package com.dev.infrastructure.repository.mappers;

import com.dev.domain.entity.Account;
import com.dev.infrastructure.repository.models.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper implements EntityMapper<AccountEntity, Account> {
    @Override
    public Account toAggregate(final AccountEntity entity) {
        return Account.restore(
                entity.getAccountId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.isActive(),
                entity.isAdmin(),
                entity.isConfirmed(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getLastAccess()
        );
    }

    @Override
    public AccountEntity fromAggregate(final Account account){
        return new AccountEntity(
                account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPassword(),
                account.isActive(),
                account.isAdmin(),
                account.isConfirmed(),
                account.getLastAccess(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
