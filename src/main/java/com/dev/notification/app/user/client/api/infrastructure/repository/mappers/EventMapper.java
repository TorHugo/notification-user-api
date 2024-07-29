package com.dev.notification.app.user.client.api.infrastructure.repository.mappers;

import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.infrastructure.repository.models.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventEntity fromAggregate(final Event event){
        return new EventEntity(
                event.getIdentifier(),
                event.getAggregateIdentifier(),
                event.getTransaction().json(),
                event.getEventType(),
                event.getCreatedAt()
        );
    }

    public Event toAggregate(final EventEntity event){
        return Event.restore(
                event.getIdentifier(),
                event.getAggregateIdentifier(),
                event.getEventType(),
                event.getTransaction(),
                event.getCreatedAt()
        );
    }
}
