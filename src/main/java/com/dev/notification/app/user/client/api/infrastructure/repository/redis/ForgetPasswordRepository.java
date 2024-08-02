package com.dev.notification.app.user.client.api.infrastructure.repository.redis;

import com.dev.notification.app.user.client.api.domain.entity.ForgetPassword;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ForgetPasswordRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Gson gson;

    @Value("${spring.data.redis.key.forget-password.prefix-name}")
    private String forgetPasswordPrefix;

    @Value("${spring.data.redis.key.forget-password.time-invalid-key}")
    private Integer forgetPasswordTimeout;

    public void save(final ForgetPassword entity){
        final var json = gson.toJson(entity);
        redisTemplate.opsForValue().set(
                buildKeyName(entity.getAggregateIdentifier()),
                json,
                forgetPasswordTimeout,
                TimeUnit.HOURS
        );
    }

    public ForgetPassword findByAggregateIdentifier(final String aggregateIdentifier) {
        final var json = (String) redisTemplate.opsForValue().get(buildKeyName(aggregateIdentifier));
        if (Objects.isNull(json) || json.isEmpty()) throw new GatewayException("Hashcode not found!", aggregateIdentifier);
        return gson.fromJson(json, ForgetPassword.class);
    }

    public boolean existsKeyByAggregateIdentifier(final String aggregateIdentifier){
        return Boolean.TRUE.equals(redisTemplate.hasKey(buildKeyName(aggregateIdentifier)));
    }

    private String buildKeyName(final String identifier){
        return forgetPasswordPrefix + ":" + identifier;
    }
}
