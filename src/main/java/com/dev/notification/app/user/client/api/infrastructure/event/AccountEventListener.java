package com.dev.notification.app.user.client.api.infrastructure.event;

import com.dev.notification.app.user.client.api.domain.entity.Event;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.EventException;
import com.dev.notification.app.user.client.api.domain.gateway.EventGateway;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.ConfirmedResetPasswordEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.event.models.SendResetPasswordEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private static final String DEFAULT_ERROR = "Error creating event!";

    private final EventGateway eventGateway;

    @EventListener
    public void handlerCreateAccount(final CreateAccountEvent entryEvent){
        try {
            final var event = Event.create(
                    entryEvent.getAggregateIdentifier(),
                    EventType.CREATE_ACCOUNT_EVENT,
                    entryEvent.getTransaction()
            );
            eventGateway.save(event);
        } catch (final Exception e) {
            throw new EventException(DEFAULT_ERROR);
        }
    }

    @EventListener
    public void handlerConfirmedAccount(final ConfirmedAccountEvent entryEvent){
        try {
            final var event = Event.create(
                    entryEvent.getAggregateIdentifier(),
                    EventType.CONFIRMED_ACCOUNT_EVENT,
                    entryEvent.getTransaction()
            );
            eventGateway.save(event);
        } catch (final Exception e) {
            throw new EventException(DEFAULT_ERROR);
        }
    }

    @EventListener
    public void handlerConfirmedAccount(final SendResetPasswordEvent entryEvent){
        try {
            final var event = Event.create(
                    entryEvent.getAggregateIdentifier(),
                    EventType.SEND_RESET_PASSWORD_EVENT,
                    entryEvent.getTransaction()
            );
            eventGateway.save(event);
        } catch (final Exception e) {
            throw new EventException(DEFAULT_ERROR);
        }
    }

    @EventListener
    public void handlerConfirmedAccount(final ConfirmedResetPasswordEvent entryEvent){
        try {
            final var event = Event.create(
                    entryEvent.getAggregateIdentifier(),
                    EventType.CONFIRMED_RESET_PASSWORD_EVENT,
                    entryEvent.getTransaction()
            );
            eventGateway.save(event);
        } catch (final Exception e) {
            throw new EventException(DEFAULT_ERROR);
        }
    }
}
