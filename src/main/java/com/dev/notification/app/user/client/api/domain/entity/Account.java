package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Email;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Account {

    private final String identifier;
    private final String firstName;
    private final String lastName;
    private final Email email;
    private String password;
    private final boolean active;
    private final boolean admin;
    private boolean confirmed;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Account(final String identifier,
                    final String firstName,
                    final String lastName,
                    final String email,
                    final String password,
                    final boolean active,
                    final boolean admin,
                    final boolean confirmed,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
        this.password = password;
        this.active = active;
        this.admin = admin;
        this.confirmed = confirmed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(final String firstName,
                                 final String lastName,
                                 final String email,
                                 final String password,
                                 final boolean admin){
        final var identifier = IdentifierUtils.unique();
        final var currentDate = LocalDateTime.now();
        return new Account(
                identifier,
                firstName,
                lastName,
                email,
                password,
                true,
                admin,
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
                                  final boolean active,
                                  final boolean admin,
                                  final boolean confirmed,
                                  final LocalDateTime createdAt,
                                  final LocalDateTime updatedAt){
        return new Account(
                identifier,
                firstName,
                lastName,
                email,
                password,
                active,
                admin,
                confirmed,
                createdAt,
                updatedAt
        );
    }

    public String getEmail() {
        return email.value();
    }

    public void isConfirmedAccount(){
        if (confirmed) throw new DomainException("This account is already confirmed!");
        this.confirmed = true;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public void updatePassword(final String password) {
        this.password = password;
    }
}
