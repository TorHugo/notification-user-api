package com.dev.notification.app.user.client.api.domain;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@UnitaryTest
class HashTokenDomainTest {
    private static final String CORRECT_PREFIX = "PREFIX";
    private static final String CORRECT_KEY = "KEY";
    private static final String CORRECT_HASH_CODE = "HASH_CODE";
    private static final LocalDateTime CORRECT_EXPIRATION_DATE = LocalDateTime.now();

    @Test
    @DisplayName("Should be create HashToken with successful!")
    void t1(){
        // given && when
        final var hashToken = HashToken.create(CORRECT_PREFIX, CORRECT_KEY, CORRECT_HASH_CODE, CORRECT_EXPIRATION_DATE);
        // then
        assertNotNull(hashToken);
        assertEquals(CORRECT_PREFIX, hashToken.getPrefix());
        assertEquals(CORRECT_KEY, hashToken.getKey());
        assertEquals(CORRECT_HASH_CODE, hashToken.getHashcode());
        assertEquals(CORRECT_EXPIRATION_DATE, hashToken.getExpirationDate());
    }

    @Test
    @DisplayName("Should be restore HashToken with successful!")
    void t2(){
        // given && when
        final var hashToken = HashToken.restore(CORRECT_PREFIX, CORRECT_KEY, CORRECT_HASH_CODE, CORRECT_EXPIRATION_DATE, CORRECT_EXPIRATION_DATE);
        // then
        assertNotNull(hashToken);
        assertEquals(CORRECT_PREFIX, hashToken.getPrefix());
        assertEquals(CORRECT_KEY, hashToken.getKey());
        assertEquals(CORRECT_HASH_CODE, hashToken.getHashcode());
        assertEquals(CORRECT_EXPIRATION_DATE, hashToken.getExpirationDate());
        assertEquals(CORRECT_EXPIRATION_DATE, hashToken.getCreatedAt());
    }

    @Test
    @DisplayName("Should be return to time of expiration date with successful!")
    void t3(){
        // given && when
        final var hashToken = HashToken.create(CORRECT_PREFIX, CORRECT_KEY, CORRECT_HASH_CODE, CORRECT_EXPIRATION_DATE);
        final var formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        final var expectedTime = hashToken.getExpirationDate().format(formatter);

        // then
        assertNotNull(hashToken);
        assertEquals(expectedTime, hashToken.getTime());
    }

    @Test
    @DisplayName("Should throws exception when expected HashToken not equal with generated!")
    void t4(){
        // given
        final var expectedException = "This hashcode is not the same as the saved one!";
        final var hashToken = HashToken.create(CORRECT_PREFIX, CORRECT_KEY, CORRECT_HASH_CODE, CORRECT_EXPIRATION_DATE);

        // when
        final var exception = assertThrows(DomainException.class, () -> hashToken.validateHashToken("12345"));

        // then
        assertNotNull(exception);
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    @DisplayName("Should throws exception when expected HashToken is expired!")
    void t5(){
        // given
        final var expectedException = "This hash-code is expired!";
        final var expectedDate = LocalDateTime.of(1, 1, 1, 1, 1);
        final var hashToken = HashToken.create(CORRECT_PREFIX, CORRECT_KEY, CORRECT_HASH_CODE, expectedDate);

        // when
        final var exception = assertThrows(DomainException.class, () -> hashToken.validateHashToken(hashToken.getHashcode()));

        // then
        assertNotNull(exception);
        assertEquals(expectedException, exception.getMessage());
    }
}
