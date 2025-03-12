package com.example.messageservice.domain.util;

import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    public String checkNull(String receiver, String sender, String content) {
        StringBuilder nullFields = new StringBuilder();

        if(sender == null) {
            nullFields.append("Sender, ");
        }

        if(receiver == null) {
            nullFields.append("Receiver, ");
        }

        if(content == null) {
            nullFields.append("Content, ");
        }

        //rimuovo l'ultima virgola
        if(!nullFields.isEmpty()) {
            nullFields.setLength(nullFields.length()-2); //rimuove lo spazio e la virgola dell'ultimo elemento
        }

        return nullFields.toString();
    }
}
