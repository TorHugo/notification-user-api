package com.dev.notification.app.user.client.api.infrastructure.repository.mappers;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.repository.models.NotificationEntity;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
    private final Gson gson;

    public NotificationEntity fromAggregate(final Notification aggregate) {
        return new NotificationEntity(
                aggregate.getIdentifier(),
                aggregate.getTo(),
                aggregate.getTemplate(),
                gson.toJson(aggregate.getParameters()),
                aggregate.getCreatedAt()
        );
    }

    public Notification toAggregate(final NotificationEntity entity){
        final List<Parameter> parameters = gson.fromJson(entity.getParameters(), List.class);
        return Notification.restore(
                entity.getIdentifier(),
                entity.getToAccount(),
                entity.getTemplate(),
                parameters,
                entity.getCreatedAt()
        );
    }
}
