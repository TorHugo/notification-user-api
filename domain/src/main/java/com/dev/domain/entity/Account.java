package com.dev.domain.entity;

import com.dev.domain.value.object.Email;
import com.dev.domain.value.object.Password;

import java.time.LocalDateTime;

public class Account {

    private final AccountIdentifier accountIdentifier;
    private final String firstName;
    private final String lastName;
    private final Email email;
    private final Password password;
    private final boolean isActive;
    private final boolean isAdmin;
    private final boolean isConfirmed;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastAccess;

    private Account(final AccountIdentifier accountIdentifier,
                    final String firstName,
                    final String lastName,
                    final String email,
                    final String password,
                    final boolean isActive,
                    final boolean isAdmin,
                    final boolean isConfirmed,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt,
                    final LocalDateTime lastAccess) {
        this.accountIdentifier = accountIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
        this.password = new Password(password);
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.isConfirmed = isConfirmed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastAccess = lastAccess;
    }

    public static Account create(final String firstName,
                                 final String lastName,
                                 final String email,
                                 final String password,
                                 final boolean isAdmin){
        final var currentIdentifier = AccountIdentifier.unique();
        final var currentDate = LocalDateTime.now();
        return new Account(
                currentIdentifier,
                firstName,
                lastName,
                email,
                password,
                true,
                isAdmin,
                false,
                currentDate,
                null,
                currentDate
        );
    }

    public static Account restore(final AccountIdentifier identifier,
                                  final String firstName,
                                  final String lastName,
                                  final String email,
                                  final String password,
                                  final boolean isActive,
                                  final boolean isAdmin,
                                  final boolean isConfirmed,
                                  final LocalDateTime createdAt,
                                  final LocalDateTime updatedAt,
                                  final LocalDateTime lastAccess){
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
                updatedAt,
                lastAccess
        );
    }

    public Account update(final String firstName,
                       final String lastName,
                       final String email,
                       final String password){
        final var currentDate = LocalDateTime.now();
        return new Account(
                getIdentifier(),
                firstName,
                lastName,
                email,
                password,
                isActive(),
                isAdmin(),
                isConfirmed(),
                getCreatedAt(),
                currentDate,
                getLastAccess()
        );
    }

    public Account inactiveAccount(){
        final var currentDate = LocalDateTime.now();
        return new Account(
                getIdentifier(),
                getFirstName(),
                getLastName(),
                getEmail(),
                getPassword(),
                false,
                isAdmin(),
                isConfirmed(),
                getCreatedAt(),
                currentDate,
                getLastAccess()
        );
    }

    public Account confirmeAccount(){
        final var currentDate = LocalDateTime.now();
        return new Account(
                getIdentifier(),
                getFirstName(),
                getLastName(),
                getEmail(),
                getPassword(),
                isActive(),
                isAdmin(),
                true,
                getCreatedAt(),
                currentDate,
                getLastAccess()
        );
    }

    public Account lastAccess(){
        final var currentDate = LocalDateTime.now();
        return new Account(
                getIdentifier(),
                getFirstName(),
                getLastName(),
                getEmail(),
                getPassword(),
                isActive(),
                isAdmin(),
                isConfirmed,
                getCreatedAt(),
                currentDate,
                currentDate
        );
    }

    public AccountIdentifier getIdentifier() {
        return accountIdentifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }
}
