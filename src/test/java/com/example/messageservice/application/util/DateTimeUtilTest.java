package com.example.messageservice.application.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
public class DateTimeUtilTest {

    @InjectMocks
    private DateTimeUtil dateTimeUtil;

    @Test
    void shouldConvertStringToOffsetDateTime_whenValidString() {
        //PARAMETERS
        String dateString  = "2019-04-05T00:00:00+00:00";

        //TEST
        OffsetDateTime result = dateTimeUtil.stringToOffsetDateTime(dateString);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(OffsetDateTime.parse(dateString), result);
    }

    @Test
    void shouldReturnNull_whenNullString() {
        //TEST
        OffsetDateTime result = dateTimeUtil.stringToOffsetDateTime(null);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldThrowException_whenInvalidString() {
        //PARAMETERS
        String invalidDateString = "";

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> {
            dateTimeUtil.stringToOffsetDateTime(invalidDateString);
        });
    }
}
