package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@IntegrationIT
class DeleteHashTokenIT {

    static final String PREFIX_CONFIRMED_ACCOUNT = "confirmed-account";
    static final String KEY_CONFIRMED_ACCOUNT = "key-account";
    static final String HASH_CODE = HashCodeUtils.create(6);
    static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now();

    @Autowired
    private HashTokenGateway hashTokenGateway;

    @Autowired
    private DeleteHashToken deleteHashToken;

    @Test
    @DisplayName("Should save delete HashToken with success!")
    void t1() {
        // given
        final var hashToken = HashToken.create(PREFIX_CONFIRMED_ACCOUNT, KEY_CONFIRMED_ACCOUNT, HASH_CODE, EXPIRATION_DATE);
        hashTokenGateway.save(hashToken);

        final var savedHashToken = hashTokenGateway.get(PREFIX_CONFIRMED_ACCOUNT, KEY_CONFIRMED_ACCOUNT);
        assertNotNull(savedHashToken);

        // when
        deleteHashToken.execute(PREFIX_CONFIRMED_ACCOUNT, KEY_CONFIRMED_ACCOUNT);

        // then
        final var deletedHashToken = hashTokenGateway.get(PREFIX_CONFIRMED_ACCOUNT, KEY_CONFIRMED_ACCOUNT);
        assertNull(deletedHashToken);
    }
}

