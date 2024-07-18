package com.dev.infrastructure.messaging;

public interface QueueProducer {
    void sendMessage(final String queue,
                     final Object message);
}
