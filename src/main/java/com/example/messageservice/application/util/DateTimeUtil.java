package com.example.messageservice.application.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Component
public class DateTimeUtil {
    @Named("stringToOffsetDateTime")
    public OffsetDateTime stringToOffsetDateTime(String dateString) {
        try {
            return dateString != null ? OffsetDateTime.parse(dateString) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date not valid: " + dateString);
        }
    }
}
