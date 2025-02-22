package com.dev.notification.app.user.client.api.infrastructure.repository.db.models;

import com.dev.notification.app.user.client.api.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Table(name = "events_tb", schema = "user_client_db")
@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    private String identifier;
    private String aggregateIdentifier;

    @Column(columnDefinition = "TEXT")
    private String transaction;

    @Enumerated(STRING)
    private EventType eventType;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
