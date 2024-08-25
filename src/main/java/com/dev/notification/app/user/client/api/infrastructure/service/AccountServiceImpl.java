package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.*;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.ServiceException;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.CreateAccountDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final FindAccount findAccount;
    private final FindAccountWithThrows findAccountWithThrows;
    private final SaveAccount saveAccount;
    private final EncryptionService encryptionService;
    private final SaveHashToken saveHashToken;
    private final PublishingService<EventPublishing> publishingService;
    private final FindHashToken findHashToken;

    @Value("${spring.integration.hash.digits.confirmed-account}")
    private Integer digits;

    @Value("${spring.integration.expiration-time.confirmed-account}")
    private Integer milliseconds;

    @Value("${spring.data.redis.key.prefix-name.confirmed-account}")
    private String prefix;

    @Override
    @Transactional
    public Account create(final CreateAccountDTO dto) {
        final var existingAccount = findAccount.execute(dto.email());
        if (Objects.nonNull(existingAccount)) throw new ServiceException("This account already exists! With email!");
        final var account = Account.create(dto.firstName(), dto.lastName(), dto.email(), encryptionService.encryption(dto.password()), false);
        final var hashToken = saveHashToken.execute(prefix, account.getIdentifier(), digits, milliseconds);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.CREATE_ACCOUNT_EVENT)
                .account(account)
                .object(Arrays.asList(
                        new Parameter("name", account.getFullName()),
                        new Parameter("hashcode", hashToken.getHashcode()),
                        new Parameter("expiration-date", hashToken.getTime())
                ))
                .build());
        return saveAccount.execute(account);
    }

    @Override
    public void confirmed(final ConfirmedHashDTO dto) {
        final var account = findAccountWithThrows.execute(dto.email());
        final var hashToken = findHashToken.execute(prefix, account.getIdentifier());
        if (Objects.isNull(hashToken)) throw new ServiceException("This hash-token does not exists!");
        hashToken.validateHashToken(dto.hash());
        account.isConfirmedAccount();
        saveAccount.execute(account);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.CONFIRMED_ACCOUNT_EVENT)
                .account(account)
                .build());
    }
}
