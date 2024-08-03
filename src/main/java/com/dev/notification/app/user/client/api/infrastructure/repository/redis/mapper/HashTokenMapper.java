package com.dev.notification.app.user.client.api.infrastructure.repository.redis.mapper;

import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.infrastructure.repository.redis.models.HashTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class HashTokenMapper {
    public HashTokenEntity fromAggregate(final HashToken domain){
        return HashTokenEntity.builder()
                .prefix(domain.getPrefix())
                .key(domain.getKey())
                .hashcode(domain.getHashcode())
                .expirationDate(domain.getExpirationDate())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public HashToken toAggregate(final HashTokenEntity entity){
        return HashToken.restore(
                entity.getPrefix(),
                entity.getKey(),
                entity.getHashcode(),
                entity.getExpirationDate(),
                entity.getCreatedAt()
        );
    }

}
