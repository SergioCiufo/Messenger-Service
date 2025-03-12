package com.example.messageservice.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class LocalDateTimeUtilTest {
    @InjectMocks
    private LocalDateTimeUtil localDateTimeUtil;

    @Test
    public void shouldConvertLocalDateTimeToString_whenAllOk(){
        //PARAMETERS
        LocalDateTime createdAt = LocalDateTime.now();
        String converted = createdAt.atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        //TEST
        String result = localDateTimeUtil.formatCreatedAt(createdAt);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(converted, result);
    }

    @Test
    public void shouldReturnDefault_whenCreatedAtIsNull(){
        //PARAMETERS
        LocalDateTime createdAt = null;

        //TEST
        String result = localDateTimeUtil.formatCreatedAt(createdAt);

        //RESULT
        Assertions.assertNotNull(result);
        Assertions.assertEquals("defaultDate", result);

    }
}
