package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.model.message.FirstStepGetMessageResponse;
import com.example.messageservice.domain.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {
    @Override
    public FirstStepGetMessageResponse getFirstStep(String username) {
        //cercare l'utente by username dalla lista utenti
        //se corrisponde usare l'id utente per trovare la lista messaggi dedicata
        if(username == null) {
            log.error("Username is null");
            throw new IllegalArgumentException("Username is null");
        }

        List<Message>username.getMessagesList(username);

        return null;
    }
}
