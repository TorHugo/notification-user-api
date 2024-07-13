package com.dev.lib.dto;

public record Account(
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        boolean isAdmin
) {
}
