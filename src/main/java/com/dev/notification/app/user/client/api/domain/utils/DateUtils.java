package com.dev.notification.app.user.client.api.domain.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private DateUtils(){}

    public static LocalDateTime fromMillis(final Integer millis) {
        final var currentDate = LocalDateTime.now();
        final var duration = Duration.of(millis, ChronoUnit.MILLIS);
        return currentDate.plus(duration).truncatedTo(ChronoUnit.SECONDS);
    }
}
