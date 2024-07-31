package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationIT
class EncryptionServiceIT {

    @Autowired
    private EncryptionService encryptionService;

    @Test
    @DisplayName("Should encrypt value with successfully.")
    void t1(){
        // Given
        final var initialValue = "value";
        // When
        final var encryption = encryptionService.encryption(initialValue);
        // Then
        assertNotNull(encryption);
        assertNotEquals(encryption, initialValue);
    }
}
