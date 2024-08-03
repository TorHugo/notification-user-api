package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.EventRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.mappers.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EventGatewayImpl implements EventGateway {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public void save(final Event event) {
        final var entity = eventMapper.fromAggregate(event);
        eventRepository.save(entity);
    }

    @Override
    public Event findByAggregateIdentifier(final String identifier) {
        final var entity = eventRepository.findByAggregateIdentifier(identifier);
        if (Objects.isNull(entity)) throw new GatewayException("Event not found with aggregate identifier.", identifier);
        return eventMapper.toAggregate(entity);
    }
}
