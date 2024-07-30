package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.NotificationRepository;
import com.dev.notification.app.user.client.api.infrastructure.repository.mappers.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationGatewayImpl implements NotificationGateway {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification save(final Notification notification) {
        final var entity = notificationMapper.fromAggregate(notification);
        notificationRepository.save(entity);
        return notificationMapper.toAggregate(entity);
    }

    @Override
    public Notification findByAccount(final String contact) {
        final var entity = notificationRepository.findByContact(contact);
        return notificationMapper.toAggregate(entity);
    }
}
