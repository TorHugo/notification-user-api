package com.dev.notification.app.user.client.api.infrastructure.repository.redis;

import com.dev.notification.app.user.client.api.infrastructure.repository.redis.models.RedisEntity;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public abstract class RedisTemplateRepository<T extends RedisEntity> {
    @Value("${spring.data.redis.key.time-invalid-key}")
    protected Integer timeInvalidKey;
    protected final RedisTemplate<String, String> redisTemplate;
    protected final Gson gson;

    public void save(final T object) {
        final var value = serialize(object);
        redisTemplate.opsForValue().set(
                buildKeyName(object),
                value,
                timeInvalidKey,
                TimeUnit.HOURS
        );
    }

    public T get(final String prefix, final String key) {
        final var json = redisTemplate.opsForValue().get(buildKeyName(prefix, key));
        if (Objects.isNull(json))
            return null;
        return deserialize(json);
    }

    public void delete(final String prefix, final String key) {
        redisTemplate.delete(buildKeyName(prefix, key));
    }

    protected String buildKeyName(final T object) {
        return object.getPrefix() + ":" + object.getKey();
    }

    protected String buildKeyName(final String prefix, final String key) {
        return prefix + ":" + key;
    }

    protected String serialize(T entity) {
        return gson.toJson(entity);
    }

    protected abstract T deserialize(String json);
}