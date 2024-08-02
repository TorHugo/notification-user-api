package com.dev.notification.app.user.client.api.application;

import com.dev.notification.app.user.client.api.domain.entity.ForgetPassword;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.gateway.ForgetPasswordGateway;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.utils.DateUtils;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SendResetPasswordUseCase {
    private final AccountGateway accountGateway;
    private final ForgetPasswordGateway forgetPasswordGateway;
    private final SendNotificationUseCase sendNotificationUseCase;
    private final PublishingService<EventPublishing> publishingService;

    @Value("${spring.integration.hash.digits.forget-password}")
    private Integer hashDigits;

    @Value("${spring.integration.expiration-time.reset-password}")
    private Integer milliseconds;

    public void execute(final String email){
        final var account = accountGateway.findAccountByEmailWithThrows(email);
        final var forgetPassword = ForgetPassword.create(
                account.getIdentifier(),
                HashCodeUtils.create(hashDigits),
                DateUtils.fromMillis(milliseconds)
        );
        sendNotificationUseCase.execute(account.getEmail(), "send-hash-forget-password", List.of(new Parameter("hash-code", forgetPassword.getHashCode())));
        publishingService.publish(EventPublishing.builder().eventType(EventType.SEND_RESET_PASSWORD_EVENT).account(account).object(forgetPassword).build());
        forgetPasswordGateway.save(forgetPassword);
    }
}
