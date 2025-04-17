package com.example.messageservice.domain.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeUtil {

//    Questo metodo si occupa di prendere un oggetto LocalDateTime e di restituirlo come stringa con fuso orario UTC
//    DateTimeFormatter.ISO_OFFSET_DATE_TIME  formatta la il tutto in una stringa con il fuso orario (UTC)

    public String formatCreatedAt(LocalDateTime createdAt){
        return createdAt != null
                ? createdAt.atZone(ZoneId.of("UTC+2")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                : "defaultDate";
    }
}
