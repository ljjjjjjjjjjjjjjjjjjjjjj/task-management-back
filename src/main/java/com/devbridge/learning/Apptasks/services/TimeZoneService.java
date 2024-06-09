package com.devbridge.learning.Apptasks.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeZoneService {
    public LocalDateTime convertToUserTimezone(LocalDateTime utcDateTime, String userTimezone) {
        return utcDateTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(userTimezone))
                .toLocalDateTime();
    }
}
