package com.dev.infrastructure.repository.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity(name = "account_tb")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountEntity extends AbstractEntity {
    @Id
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isActive;
    private boolean isAdmin;
    private boolean isConfirmed;
    private LocalDateTime lastAccess;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
