package com.dev.notification.app.user.client.api.domain.gateway;

import com.dev.notification.app.user.client.api.domain.entity.ForgetPassword;

public interface ForgetPasswordGateway {
    void save(final ForgetPassword forgetPassword);
    ForgetPassword findByAggregateIdentifier(final String aggregateIdentifier);
    boolean existsKey(final String aggregateIdentifier);
}
