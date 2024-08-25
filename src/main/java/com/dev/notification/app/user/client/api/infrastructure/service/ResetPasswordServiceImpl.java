package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.SaveAccount;
import com.dev.notification.app.user.client.api.application.SaveHashToken;
import com.dev.notification.app.user.client.api.application.FindAccountWithThrows;
import com.dev.notification.app.user.client.api.application.FindHashToken;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.exception.template.ServiceException;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.ResetPasswordService;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EncryptedPassword;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final FindAccountWithThrows findAccountWithThrows;
    private final EncryptionService encryptionService;
    private final SaveHashToken saveHashToken;
    private final SaveAccount saveAccount;
    private final FindHashToken findHashToken;
    private final PublishingService<EventPublishing> publishingService;

    @Value("${spring.data.redis.key.prefix-name.reset-password}")
    private String prefix;

    @Value("${spring.integration.hash.digits.forget-password}")
    private Integer hashDigits;

    @Value("${spring.integration.expiration-time.reset-password}")
    private Integer milliseconds;

    @Override
    public void sendHashToken(final String email) {
        final var account = findAccountWithThrows.execute(email);
        final var hashToken = saveHashToken.execute(
                prefix,
                account.getIdentifier(),
                hashDigits,
                milliseconds
        );
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.SEND_RESET_PASSWORD_EVENT)
                .account(account)
                .object(hashToken).build());
    }

    /**
     * Confirms the password reset for a user based on a provided hash code.
     * This method performs a sequence of steps:
     * 1. Validates if the user exists based on the provided email.
     * 2. Checks if the previously sent hash code is valid and not expired.
     * 3. Creates a valid temporary password.
     * 4. Encrypts the temporary password for sending to the authentication topic.
     * 5. Sends the temporary password to the user's email.
     * 6. Publishes a password reset confirmation event.
     *
     * @param dto The {@link ConfirmedHashDTO} object containing:
     *            - user's email
     *            - previously sent hash code.
     */
    public void confirmed(final ConfirmedHashDTO dto){
        final var account = findAccountWithThrows.execute(dto.email());
        final var hashToken = findHashToken.execute(prefix, account.getIdentifier());
        if (Objects.isNull(hashToken)) throw new ServiceException("This hash-token does not exists!");
        hashToken.validateHashToken(dto.hash());
        final var temporaryPassword = HashCodeUtils.generateValidPassword();
        final var encryptedPassword = encryptionService.encryption(temporaryPassword);
        account.updatePassword(encryptedPassword);
        saveAccount.execute(account);
        publishingService.publish(EventPublishing.builder()
                .eventType(EventType.CONFIRMED_RESET_PASSWORD_EVENT)
                .account(account)
                .object(new EncryptedPassword(encryptedPassword, temporaryPassword))
                .build());
    }

}
