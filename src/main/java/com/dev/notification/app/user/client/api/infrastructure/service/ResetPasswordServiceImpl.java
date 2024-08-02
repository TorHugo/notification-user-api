package com.dev.notification.app.user.client.api.infrastructure.service;

import com.dev.notification.app.user.client.api.application.ConfirmedResetPasswordUseCase;
import com.dev.notification.app.user.client.api.application.SendNotificationUseCase;
import com.dev.notification.app.user.client.api.domain.enums.EventType;
import com.dev.notification.app.user.client.api.domain.gateway.AccountGateway;
import com.dev.notification.app.user.client.api.domain.service.EncryptionService;
import com.dev.notification.app.user.client.api.domain.service.PublishingService;
import com.dev.notification.app.user.client.api.domain.service.ResetPasswordService;
import com.dev.notification.app.user.client.api.domain.utils.HashCodeUtils;
import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.dev.notification.app.user.client.api.infrastructure.api.models.request.ConfirmedHashDTO;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EncryptedPassword;
import com.dev.notification.app.user.client.api.infrastructure.service.models.EventPublishing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final AccountGateway accountGateway;
    private final ConfirmedResetPasswordUseCase confirmedResetPasswordUseCase;
    private final SendNotificationUseCase sendNotificationUseCase;
    private final EncryptionService encryptionService;
    private final PublishingService<EventPublishing> publishingService;

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
        final var account = accountGateway.findAccountByEmailWithThrows(dto.email());
        confirmedResetPasswordUseCase.execute(account, dto.hash());
        final var temporaryPassword = HashCodeUtils.generateValidPassword();
        final var encryptedPassword = encryptionService.encryption(temporaryPassword);
        sendNotificationUseCase.execute(dto.email(), "temporary-password", List.of(new Parameter("temporary-password", temporaryPassword)));
        publishingService.publish(EventPublishing.builder().eventType(EventType.CONFIRMED_RESET_PASSWORD_EVENT).account(account).object(new EncryptedPassword(encryptedPassword)).build());
    }

}
