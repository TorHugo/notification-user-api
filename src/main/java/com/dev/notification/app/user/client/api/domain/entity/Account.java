package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.value.object.Email;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Account {

    private final String identifier;
    private final String firstName;
    private final String lastName;
    private final Email email;
    private final String password;
    private final boolean isActive;
    private final boolean isAdmin;
    private final boolean isConfirmed;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Account(final String identifier,
                    final String firstName,
                    final String lastName,
                    final String email,
                    final String password,
                    final boolean isActive,
                    final boolean isAdmin,
                    final boolean isConfirmed,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
        this.password = password;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.isConfirmed = isConfirmed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(final String firstName,
                                 final String lastName,
                                 final String email,
                                 final String password,
                                 final boolean isAdmin){
        final var identifier = IdentifierUtils.unique();
        final var currentDate = LocalDateTime.now();
        return new Account(
                identifier,
                firstName,
                lastName,
                email,
                password,
                true,
                isAdmin,
                false,
                currentDate,
                null
        );
    }

    public static Account restore(final String identifier,
                                  final String firstName,
                                  final String lastName,
                                  final String email,
                                  final String password,
                                  final boolean isActive,
                                  final boolean isAdmin,
                                  final boolean isConfirmed,
                                  final LocalDateTime createdAt,
                                  final LocalDateTime updatedAt){
        return new Account(
                identifier,
                firstName,
                lastName,
                email,
                password,
                isActive,
                isAdmin,
                isConfirmed,
                createdAt,
                updatedAt
        );
    }

    public String getEmail() {
        return email.value();
    }
}
