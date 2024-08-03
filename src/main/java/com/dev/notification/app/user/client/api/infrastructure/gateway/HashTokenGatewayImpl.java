package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.redis.HashTokenRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.redis.mapper.HashTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashTokenGatewayImpl implements HashTokenGateway {
    private final HashTokenRepository hashTokenRepository;
    private final HashTokenMapper hashTokenMapper;

    @Override
    public void save(final HashToken domain) {
        final var entity = hashTokenMapper.fromAggregate(domain);
        hashTokenRepository.save(entity);
    }

    @Override
    public HashToken get(final String prefix, final String key) {
        final var entity = hashTokenRepository.get(prefix, key);
        return hashTokenMapper.toAggregate(entity);
    }
}
