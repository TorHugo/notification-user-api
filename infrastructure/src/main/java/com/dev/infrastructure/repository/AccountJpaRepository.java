package com.dev.infrastructure.repository;

import com.dev.infrastructure.repository.models.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {
    AccountEntity findByEmail(final String email);
}
