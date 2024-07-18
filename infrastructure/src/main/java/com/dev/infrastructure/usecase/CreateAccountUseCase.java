package com.dev.infrastructure.usecase;

import com.dev.domain.entity.Account;
import com.dev.domain.exception.template.InvalidArgumentException;
import com.dev.domain.gateway.AccountGateway;
import com.dev.infrastructure.api.models.request.AccountCreateRequest;
import com.dev.infrastructure.messaging.QueueProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.dev.domain.enums.MessageErrorEnum.OBJECT_ALREADY_EXISTS_EXCEPTION;

@Component
public class CreateAccountUseCase {
    @Value("${api.integration.queue.send_confirmed_account}")
    private String sendConfirmedAccountQueue;

    private final AccountGateway accountGateway;
    private final QueueProducer queueProducer;

    public CreateAccountUseCase(final AccountGateway accountGateway,
                                final QueueProducer queueProducer) {
        this.accountGateway = accountGateway;
        this.queueProducer = queueProducer;
    }

    public String execute(final AccountCreateRequest account){
        final var existingAccount = accountGateway.findAccountByEmailOrNull(account.email());
        if (Objects.nonNull(existingAccount))
            throw new InvalidArgumentException(OBJECT_ALREADY_EXISTS_EXCEPTION.code(), "This account with email", account.email());

        final var currentAccount = Account.create(
                account.firstName(),
                account.lastName(),
                account.email(),
                account.password(),
                false
        );
        this.accountGateway.saveToAccount(currentAccount);
        this.queueProducer.sendMessage(sendConfirmedAccountQueue, account.email());
        return currentAccount.getIdentifier();
    }
}
