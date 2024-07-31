package com.dev.notification.app.user.client.api.domain;

import com.dev.notification.app.user.client.api.annotation.UnitaryTest;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@UnitaryTest
class NotificationDomainTest {
    private static final String CONTACT = "email@example.com";
    private static final String TEMPLATE = "template";
    private static final List<Parameter> PARAMETERS = List.of(new Parameter("name", "value"));
    private static final List<Parameter> PARAMETERS_IS_EMPTY = new ArrayList<>();

    @Test
    @DisplayName("Should be instantiated Notification with success.")
    void t1(){
        // Given && When
        final var notification = Notification.create(CONTACT, TEMPLATE, PARAMETERS);
        // Then
        assertNotNull(notification);
        assertNotNull(notification.getIdentifier());
        assertEquals(CONTACT, notification.getContact());
        assertEquals(TEMPLATE, notification.getTemplate());
        assertEquals(PARAMETERS, notification.getParameters());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    @DisplayName("Should be instantiated Notification with success when parameters is empty.")
    void t2(){
        // Given && When
        final var notification = Notification.create(CONTACT, TEMPLATE, PARAMETERS_IS_EMPTY);
        // Then
        assertNotNull(notification);
        assertNotNull(notification.getIdentifier());
        assertEquals(CONTACT, notification.getContact());
        assertEquals(TEMPLATE, notification.getTemplate());
        assertEquals(PARAMETERS_IS_EMPTY, notification.getParameters());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    @DisplayName("Should be restore Notification with success.")
    void t3(){
        // Given
        final var expectedIdentifier = IdentifierUtils.unique();
        final var expectedCurrentDate = LocalDateTime.now();
        // When
        final var notification = Notification.restore(expectedIdentifier, CONTACT, TEMPLATE, PARAMETERS, expectedCurrentDate);
        // Then
        assertNotNull(notification);
        assertEquals(expectedIdentifier, notification.getIdentifier());
        assertEquals(CONTACT, notification.getContact());
        assertEquals(TEMPLATE, notification.getTemplate());
        assertEquals(PARAMETERS, notification.getParameters());
        assertEquals(expectedCurrentDate, notification.getCreatedAt());
    }

    @Test
    @DisplayName("Should be throws exception when contact is null.")
    void t4(){
        // Given
        final var expectedMessageError = "Contact must be not null or empty!";
        // When
        final var notification = assertThrows(DomainException.class, ()-> Notification.create(null, TEMPLATE, PARAMETERS));
        // Then
        assertNotNull(notification);
        assertEquals(expectedMessageError, notification.getMessage());
    }

    @Test
    @DisplayName("Should be throws exception when contact is empty.")
    void t5(){
        // Given
        final var expectedMessageError = "Contact must be not null or empty!";
        // When
        final var notification = assertThrows(DomainException.class, ()-> Notification.create("", TEMPLATE, PARAMETERS));
        // Then
        assertNotNull(notification);
        assertEquals(expectedMessageError, notification.getMessage());
    }

    @Test
    @DisplayName("Should be throws exception when template is null.")
    void t6(){
        // Given
        final var expectedMessageError = "Template must be not null or empty!";
        // When
        final var notification = assertThrows(DomainException.class, ()-> Notification.create(CONTACT, null, PARAMETERS));
        // Then
        assertNotNull(notification);
        assertEquals(expectedMessageError, notification.getMessage());
    }

    @Test
    @DisplayName("Should be throws exception when template is empty.")
    void t7(){
        // Given
        final var expectedMessageError = "Template must be not null or empty!";
        // When
        final var notification = assertThrows(DomainException.class, ()-> Notification.create(CONTACT, "", PARAMETERS));
        // Then
        assertNotNull(notification);
        assertEquals(expectedMessageError, notification.getMessage());
    }

    @Test
    @DisplayName("Should be find parameter by name.")
    void t8(){
        // Given
        final var expectedParameter = new Parameter("name", "value");
        // When
        final var notification = Notification.create(CONTACT, TEMPLATE, PARAMETERS);
        final var parameter = notification.findByName("name");

        // Then
        assertNotNull(notification);
        assertEquals(expectedParameter, parameter);
    }

    @Test
    @DisplayName("Should be throws exception when parameter by name not found.")
    void t9(){
        // Given
        final var expectedMessageError = "This parameter is not a valid!";
        // When
        final var notification = Notification.create(CONTACT, TEMPLATE, PARAMETERS);
        final var parameter = assertThrows(DomainException.class, ()-> notification.findByName("error"));

        // Then
        assertNotNull(notification);
        assertEquals(expectedMessageError, parameter.getMessage());
    }
}
