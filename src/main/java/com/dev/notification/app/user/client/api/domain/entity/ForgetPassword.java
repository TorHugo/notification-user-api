package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ForgetPassword {
    private final String identifier;
    private final String aggregateIdentifier;
    private final String hashCode;
    private final LocalDateTime expirationDate;
    private final LocalDateTime createdAt;

    private ForgetPassword(final String identifier,
                           final String aggregateIdentifier,
                           final String hashCode,
                           final LocalDateTime expirationDate,
                           final LocalDateTime createdAt) {
        this.identifier = identifier;
        this.aggregateIdentifier = aggregateIdentifier;
        this.hashCode = hashCode;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public static ForgetPassword create(final String aggregateIdentifier,
                                        final String hashcode,
                                        final LocalDateTime expirationDate){
        final var currentDate = LocalDateTime.now();
        return new ForgetPassword(
                IdentifierUtils.unique(),
                aggregateIdentifier,
                hashcode,
                expirationDate,
                currentDate
        );
    }
}
