package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.NotificationRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.db.mappers.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class NotificationGatewayImpl implements NotificationGateway {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void save(final Notification notification) {
        final var entity = notificationMapper.fromAggregate(notification);
        notificationRepository.save(entity);
    }

    @Override
    public Notification findByContact(final String contact) {
        final var entity = notificationRepository.findByContact(contact);
        if (Objects.isNull(entity)) throw new GatewayException("Notification not found, for this contact.", contact);
        return notificationMapper.toAggregate(entity);
    }
}
