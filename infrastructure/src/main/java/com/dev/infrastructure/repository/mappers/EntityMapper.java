package com.dev.infrastructure.repository.mappers;

import com.dev.domain.AbstractAggregate;
import com.dev.infrastructure.repository.models.AbstractEntity;

public interface EntityMapper<E extends AbstractEntity, A extends AbstractAggregate> {
    E fromAggregate(final A aggregate);
    A toAggregate(final E entity);
}
