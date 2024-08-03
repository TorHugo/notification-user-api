package com.dev.notification.app.user.client.api.infrastructure.repository.redis.models;

public interface RedisEntity {
    String getKey();
    String getPrefix();
}