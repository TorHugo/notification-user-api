package com.dev.notification.app.user.client.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateMessage {
    CONFIRMED_ACCOUNT("Hey, confirme a sua conta :)", "Olá, `name`, tudo bem?\nUtilize o código abaixo para confirmar a sua conta!\n\nCódigo: `hashcode`\nExpiração: `expiration-date`!");
    private final String subject;
    private final String template;
}
