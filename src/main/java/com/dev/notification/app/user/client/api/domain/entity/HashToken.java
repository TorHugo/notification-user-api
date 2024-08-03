package com.dev.notification.app.user.client.api.domain.entity;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class HashToken {
    private final String prefix;
    private final String key;
    private final String hashcode;
    private final LocalDateTime expirationDate;
    private final LocalDateTime createdAt;

    private HashToken(final String prefix,
                      final String key,
                      final String hashcode,
                      final LocalDateTime expirationDate,
                      final LocalDateTime createdAt) {
        this.prefix = prefix;
        this.key = key;
        this.hashcode = hashcode;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public static HashToken create(
            final String prefix,
            final String aggregateIdentifier,
            final String hashcode,
            final LocalDateTime expirationDate
    ){
        return new HashToken(
                prefix,
                aggregateIdentifier,
                hashcode,
                expirationDate,
                LocalDateTime.now()
        );
    }

    public static HashToken restore(final String prefix,
                                    final String aggregateIdentifier,
                                    final String hashcode,
                                    final LocalDateTime expirationDate,
                                    final LocalDateTime createdAt){
        return new HashToken(
                prefix,
                aggregateIdentifier,
                hashcode,
                expirationDate,
                createdAt
        );
    }
}
