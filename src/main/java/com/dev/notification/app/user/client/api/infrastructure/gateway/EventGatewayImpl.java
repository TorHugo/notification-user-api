package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.EventRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.mappers.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
