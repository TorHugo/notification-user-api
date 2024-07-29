package com.dev.notification.app.user.client.api.infrastructure.repository;

import com.dev.notification.app.user.client.api.infrastructure.repository.models.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    AccountEntity findByEmail(final String email);
}
