package com.dev.notification.app.user.client.api.infrastructure.repository.redis.template;

import com.dev.notification.app.user.client.api.infrastructure.repository.redis.RedisTemplateRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.redis.models.HashTokenEntity;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class HashTokenRepository extends RedisTemplateRepository<HashTokenEntity> {

    public HashTokenRepository(final RedisTemplate<String, String> redisTemplate,
                               final Gson gson) {
        super(redisTemplate, gson);
    }

    @Override
    protected HashTokenEntity deserialize(final String json) {
        return gson.fromJson(json, HashTokenEntity.class);
    }
}