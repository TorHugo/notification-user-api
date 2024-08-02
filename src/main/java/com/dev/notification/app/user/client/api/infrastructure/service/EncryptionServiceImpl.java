package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encryption(final String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    @Override
    public boolean matches(final String expectedValue,
                           final String actualValue) {
        return BCrypt.checkpw(expectedValue, actualValue);
    }
}
