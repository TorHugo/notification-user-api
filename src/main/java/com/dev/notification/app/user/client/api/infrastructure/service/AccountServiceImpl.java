package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.CreateAccount;
import com.dev.notification.app.user.client.api.application.FindAccount;
import com.dev.notification.app.user.client.api.domain.entity.Account;
import com.dev.notification.app.user.client.api.domain.entity.HashToken;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.GatewayException;
import com.dev.notification.app.user.client.api.domain.gateway.HashTokenGateway;
import com.dev.notification.app.user.client.api.domain.service.AccountService;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
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
    private final CreateAccount createAccount;
    private final EncryptionService encryptionService;
    private final HashTokenGateway hashTokenGateway;
    private final PublishingService<EventPublishing> publishingService;

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
        if (Objects.nonNull(existingAccount)) throw new GatewayException("This account already exists! With email:", dto.email());
        final var account = Account.create(dto.firstName(), dto.lastName(), dto.email(), encryptionService.encryption(dto.password()), false);
        final var hashToken = HashToken.create(prefix, account.getIdentifier(), HashCodeUtils.create(digits), DateUtils.fromMillis(milliseconds));
        hashTokenGateway.save(hashToken);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.CREATE_ACCOUNT_EVENT)
                .account(account)
                .object(Arrays.asList(new Parameter("hashcode", hashToken.getHashcode()), new Parameter("confirmed", "false"),new Parameter("expiration-date", hashToken.getExpirationDate().toString())))
                .build());
        return createAccount.execute(account);
    }
}
