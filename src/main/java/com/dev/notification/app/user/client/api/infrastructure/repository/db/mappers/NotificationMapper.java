package com.dev.notification.app.user.client.api.infrastructure.repository.db.mappers;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.models.NotificationEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
                aggregate.getContact(),
                aggregate.getSubject(),
                aggregate.getTemplate(),
                gson.toJson(aggregate.getParameters()),
                aggregate.getCreatedAt()
        );
    }

    public Notification toAggregate(final NotificationEntity entity){
        final var parameterListType = new TypeToken<List<Parameter>>() {}.getType();
        final List<Parameter> parameters = gson.fromJson(entity.getParameters(), parameterListType);
        return Notification.restore(
                entity.getIdentifier(),
                entity.getContact(),
                entity.getSubject(),
                entity.getTemplate(),
                parameters,
                entity.getCreatedAt()
        );
    }

}
