package com.dev.notification.app.user.client.api.infrastructure.repository.redis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HashTokenEntity implements RedisEntity {
    private String prefix;
    private String key;
    private String hashcode;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getKey() {
        return key;
    }
}