package com.dev.notification.app.user.client.api.domain.utils;

import java.util.Random;

public class HashCodeUtils {
    private HashCodeUtils(){

    }
    private static final Random random = new Random();

    public static String create(final Integer digits) {
        final var hash = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            int randomNumber = random.nextInt(10);
            hash.append(randomNumber);
        }
        return hash.toString();
    }
}
