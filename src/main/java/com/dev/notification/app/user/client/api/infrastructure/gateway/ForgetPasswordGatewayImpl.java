package com.dev.notification.app.user.client.api.infrastructure.gateway;

import com.dev.notification.app.user.client.api.domain.entity.ForgetPassword;
import com.dev.notification.app.user.client.api.domain.gateway.ForgetPasswordGateway;
import com.dev.notification.app.user.client.api.infrastructure.repository.redis.ForgetPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgetPasswordGatewayImpl implements ForgetPasswordGateway {
    private final ForgetPasswordRepository forgetPasswordRepository;

    @Override
    public void save(final ForgetPassword domain) {
        forgetPasswordRepository.save(domain);
    }

    @Override
    public ForgetPassword findByAggregateIdentifier(final String aggregateIdentifier) {
        return forgetPasswordRepository.findByAggregateIdentifier(aggregateIdentifier);
    }

    @Override
    public boolean existsKey(final String aggregateIdentifier) {
        return forgetPasswordRepository.existsKeyByAggregateIdentifier(aggregateIdentifier);
    }
}
