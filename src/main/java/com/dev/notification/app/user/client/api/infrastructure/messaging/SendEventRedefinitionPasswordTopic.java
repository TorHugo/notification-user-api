package com.dev.notification.app.user.client.api.infrastructure.messaging;

import com.dev.notification.app.user.client.api.infrastructure.messaging.models.RedefinitionPasswordTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.producer.KafkaProducerService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEventRedefinitionPasswordTopic {
    private final KafkaProducerService kafkaProducerService;
    private final Gson gson;

    @Value("${spring.kafka.producer.send-event-reset-password-topic}")
    private String topic;

    public void execute(final RedefinitionPasswordTopic message){
        kafkaProducerService.sendMessage(topic, gson.toJson(message));
    }
}
