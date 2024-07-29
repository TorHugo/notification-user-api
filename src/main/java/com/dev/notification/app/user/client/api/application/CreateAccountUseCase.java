package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.CreateAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.event.models.CreateAccountEvent;
import com.dev.notification.app.user.client.api.infrastructure.messaging.CreateAccountEventTopic;
import com.dev.notification.app.user.client.api.infrastructure.messaging.models.CreateAccountTopic;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateAccountUseCase {
    private final AccountGateway accountGateway;
    private final EncryptionService encryptionService;
    private final ApplicationEventPublisher eventPublisher;
    private final CreateAccountEventTopic createAccountEventTopic;
    private final Gson gson;

    public Account execute(final CreateAccountDTO dto){
        final var existingAccount = accountGateway.findAccountByEmail(dto.email());
        if (Objects.nonNull(existingAccount))
            throw new GatewayException("This account already exists! With email:", dto.email());
        final var encryptedPassword = encryptionService.encryption(dto.password());
        final var account = Account.create(dto.firstName(), dto.lastName(), dto.email(), encryptedPassword, false);
        eventPublisher.publishEvent(new CreateAccountEvent(this, account.getIdentifier(), gson.toJson(account)));
        createAccountEventTopic.execute(new CreateAccountTopic(account.getEmail().value(), account.getPassword(), account.isAdmin()));
        // SEND NotificationEventTopic
        return accountGateway.saveToAccount(account);
    }
}
