package com.dev.notification.app.user.client.api.infrastructure.messaging;

import com.dev.notification.app.user.client.api.infrastructure.messaging.models.NotificationTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.producer.KafkaProducerService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEventNotificationTopic {
    private final KafkaProducerService kafkaProducerService;
    private final Gson gson;

    @Value("${spring.kafka.producer.send-event-notification-topic}")
    private String topic;

    public void execute(final NotificationTopic message){
        kafkaProducerService.sendMessage(topic, gson.toJson(message));
    }
}
