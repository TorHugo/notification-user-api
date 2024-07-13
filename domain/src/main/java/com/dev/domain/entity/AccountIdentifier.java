package com.dev.domain.entity;

import com.dev.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class AccountIdentifier extends Identifier {
    private final String value;
    public AccountIdentifier(String value) {
        this.value = Objects.requireNonNull(value);
    }
    public static AccountIdentifier unique(){
        return new AccountIdentifier(UUID.randomUUID().toString());
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
