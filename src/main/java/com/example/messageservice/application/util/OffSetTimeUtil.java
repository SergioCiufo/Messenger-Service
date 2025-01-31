package com.example.messageservice.application.util;

import java.time.OffsetDateTime;

public class OffSetTimeUtil {
    public OffsetDateTime stringToOffsetDateTime(String dateString) {
        return dateString != null ? OffsetDateTime.parse(dateString) : null;
    }
}
