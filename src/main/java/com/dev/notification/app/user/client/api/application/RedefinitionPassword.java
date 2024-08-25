package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.DomainException;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.RedefinitionPasswordDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EncryptedPassword;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedefinitionPassword {
    private final AccountGateway accountGateway;
    private final EncryptionService encryptionService;
    private final PublishingService<EventPublishing> publishingService;

    public void execute(final RedefinitionPasswordDTO dto){
        final var account = accountGateway.findAccountByEmailWithThrows(dto.email());
        if (!encryptionService.matches(dto.oldPassword(), account.getPassword())) throw new DomainException("The password passed is not the same as the saved one!");
        final var newPasswordEncrypted = encryptionService.encryption(dto.newPassword());
        account.updatePassword(newPasswordEncrypted);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.REDEFINITION_PASSWORD_EVENT)
                .account(account)
                .object(new EncryptedPassword(newPasswordEncrypted, ""))
                .build());
        accountGateway.save(account);
    }
}
