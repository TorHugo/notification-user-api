package com.dev.notification.app.user.client.api.infrastructure.repository.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "account_tb", schema = "user_client_db")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    private String identifier;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private boolean admin;
    private boolean confirmed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
