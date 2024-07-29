package com.dev.notification.app.user.client.api.infrastructure.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(final String topicName, final String message) {
        kafkaTemplate.send(topicName, message);
    }
}
