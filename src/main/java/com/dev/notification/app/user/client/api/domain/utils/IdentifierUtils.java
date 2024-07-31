package com.dev.notification.app.user.client.api.domain.utils;

import java.util.UUID;

public class IdentifierUtils {
    private IdentifierUtils(){}

    public static String unique(){
        return UUID.randomUUID().toString();
    }
}
