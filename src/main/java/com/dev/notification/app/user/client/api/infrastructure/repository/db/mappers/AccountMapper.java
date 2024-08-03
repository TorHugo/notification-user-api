package com.dev.notification.app.user.client.api.infrastructure.repository.db.mappers;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.models.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

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
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }

    public Account toAggregate(final AccountEntity entity){
        return Account.restore(
                entity.getIdentifier(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.isActive(),
                entity.isAdmin(),
                entity.isConfirmed(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
