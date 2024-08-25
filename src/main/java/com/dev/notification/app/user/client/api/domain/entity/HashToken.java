package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    public String getTime(){
        final var formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.expirationDate.format(formatter);
    }

    public void validateHashToken(final String expectedHashToken){
        final var currentDate = LocalDateTime.now();
        if (!Objects.equals(hashcode, expectedHashToken)) throw new DomainException("This hashcode is not the same as the saved one!");
        if (expirationDate.isBefore(currentDate)) throw new DomainException("This hash-code is expired!");
    }
}
