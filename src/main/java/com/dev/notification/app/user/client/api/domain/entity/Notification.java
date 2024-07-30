package com.dev.notification.app.user.client.api.domain.entity;

import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.utils.IdentifierUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
public class Notification {
    private final String identifier;
    private final String contact;
    private final String template;
    private final List<Parameter> parameters;
    private final LocalDateTime createdAt;

    private Notification(
            final String identifier,
            final String contact,
            final String template,
            final List<Parameter> parameters,
            final LocalDateTime createdAt) {
        this.identifier = identifier;
        this.contact = contact;
        this.template = template;
        this.parameters = parameters;
        this.createdAt = createdAt;
    }

    public static Notification create(
            final String to,
            final String template,
            final List<Parameter> parameters
    ){
        return new Notification(
                IdentifierUtils.unique(),
                to,
                template,
                parameters,
                LocalDateTime.now()
        );
    }

    public static Notification restore(
            final String identifier,
            final String to,
            final String template,
            final List<Parameter> parameters,
            final LocalDateTime createdAt
    ){
        return new Notification(
                identifier,
                to,
                template,
                parameters,
                createdAt
        );
    }

    public Parameter findByName(final String name){
        return parameters.stream().filter(
                        parameter -> Objects.equals(parameter.name(), name)
                ).findFirst()
                .orElseThrow(
                        ()-> new DomainException("This parameter is not a valid!", name)
                );
    }
}
