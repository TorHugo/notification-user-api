package com.dev.notification.app.user.client.api.domain.validation;

import com.dev.notification.app.user.client.api.domain.Password;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null)
            throw new DomainException("Password cannot be null");
        if (password.length() < 8)
            throw new DomainException("This password must be at least 8 characters.");
        if (!password.matches(".*[A-Z].*"))
            throw new DomainException("This password does not contain uppercase letters.");
        if (!password.matches(".*[a-z].*"))
            throw new DomainException("This password does not contain lowercase letters.");
        if (!password.matches(".*\\W.*"))
            throw new DomainException("This password must have a special character.");
        return true;
    }
}
