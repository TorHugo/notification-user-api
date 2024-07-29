package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.FindAccountUseCase;
import com.dev.notification.app.user.client.api.application.SaveAccountUseCase;
import com.dev.notification.app.user.client.api.application.SaveNotificationUseCase;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.Notification;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.models.PublishingEventCreateAccount;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.api.models.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final FindAccountUseCase findAccountUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final SaveNotificationUseCase saveNotificationUseCase;
    private final EncryptionService encryptionService;
    private final PublishingService publishingService;

    @Value("${spring.integration.email-token.hash-digits}")
    private Integer digits;

    @Value("${spring.integration.email-token.expiration-date}")
    private Integer milliseconds;

    @Override
    public Account create(final CreateAccountDTO dto) {
        final var existingAccount = findAccountUseCase.execute(dto.email());
        if (Objects.nonNull(existingAccount)) throw new GatewayException("This account already exists! With email:", dto.email());
        final var account = Account.create(dto.firstName(), dto.lastName(), dto.email(), encryptionService.encryption(dto.password()), false);
        final var notification = Notification.create(account.getEmail(), "", Arrays.asList(new Parameter("hashcode", HashCodeUtils.create(digits)), new Parameter("process", "false"),new Parameter("expiration-date", DateUtils.fromMillis(milliseconds).toString())));
        publishingService.publish(PublishingEventCreateAccount.builder()
                .account(account)
                .notification(notification)
                .build());
        saveNotificationUseCase.execute(notification);
        return saveAccountUseCase.execute(account);
    }
}
