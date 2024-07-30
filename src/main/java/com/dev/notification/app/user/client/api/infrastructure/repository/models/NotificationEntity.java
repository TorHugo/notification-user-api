package com.dev.notification.app.user.client.api.infrastructure.repository.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "notification_tb", schema = "user_client_db")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationEntity {
    @Id
    private String identifier;
    private String contact;
    private String template;
    @Column(columnDefinition = "TEXT")
    private String parameters;
    private LocalDateTime createdAt;
}
