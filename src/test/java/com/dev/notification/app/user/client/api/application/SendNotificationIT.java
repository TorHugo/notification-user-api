package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.annotation.IntegrationIT;
import com.dev.notification.app.user.client.api.domain.gateway.NotificationGateway;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationIT
class SendNotificationIT {

    @Autowired
    private SendNotification sendNotification;

    @Autowired
    private NotificationGateway notificationGateway;

    private static final String CONTACT = "email@example.com";
    private static final String TEMPLATE = "template";
    private static final List<Parameter> PARAMETERS = List.of(new Parameter("name", "value"));
    private static final List<Parameter> PARAMETERS_EMPTY = new ArrayList<>();

    @Test
    @DisplayName("Should save and send notification, with successfully.")
    void t1(){
        //Given && When
        sendNotification.execute(CONTACT, "", TEMPLATE, PARAMETERS);
        //Then
        final var currentNotification = notificationGateway.findByContact(CONTACT);
        assertNotNull(currentNotification);
        assertNotNull(currentNotification.getIdentifier());
        assertEquals(CONTACT, currentNotification.getContact());
        assertEquals(TEMPLATE, currentNotification.getTemplate());
        assertEquals(PARAMETERS, currentNotification.getParameters());
        assertNotNull(currentNotification.getCreatedAt());
    }

    @Test
    @DisplayName("Should save and send notification when parameters is empty, with successfully.")
    void t2(){
        //Given && When
        sendNotification.execute(CONTACT, "", TEMPLATE, PARAMETERS_EMPTY);
        //Then
        final var currentNotification = notificationGateway.findByContact(CONTACT);
        assertNotNull(currentNotification);
        assertNotNull(currentNotification.getIdentifier());
        assertEquals(CONTACT, currentNotification.getContact());
        assertEquals(TEMPLATE, currentNotification.getTemplate());
        assertEquals(PARAMETERS_EMPTY, currentNotification.getParameters());
        assertNotNull(currentNotification.getCreatedAt());
    }
}
