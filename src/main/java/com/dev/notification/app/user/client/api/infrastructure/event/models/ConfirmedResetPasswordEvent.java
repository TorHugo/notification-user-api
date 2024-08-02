package com.dev.notification.app.user.client.api.infrastructure.event.models;

import com.dev.notification.app.user.client.api.infrastructure.event.BaseEvent;
import lombok.Getter;

@Getter
public class ConfirmedResetPasswordEvent extends BaseEvent {
    public ConfirmedResetPasswordEvent(final Object source,
                                       final String aggregateIdentifier,
                                       final String transaction) {
        super(source, aggregateIdentifier, transaction);
    }
}
