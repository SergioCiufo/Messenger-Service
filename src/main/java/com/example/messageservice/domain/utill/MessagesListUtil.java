package com.example.messageservice.domain.utill;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessagesListUtil {

    private List<Message> messages = new ArrayList<>(){
        {
            User admin = new User(1, "admin", "admin", new ArrayList<>(), new ArrayList<>());
            User name = new User(2, "name", "username", new ArrayList<>(), new ArrayList<>());
            User test = new User(3, "test", "test", new ArrayList<>(), new ArrayList<>());
            User sergio = new User(3, "sergio", "sergio", new ArrayList<>(), new ArrayList<>());

            add(new Message(1, "Ciao name!", admin, name, LocalDateTime.of(2025, 01, 31, 07, 15, 30), true));
            add(new Message(2, "Ciao admin! Spero tutto bene", name, admin, LocalDateTime.of(2025, 01, 31, 07, 30, 30), false));
            add(new Message(3, "Ciao Sergio!", test, sergio, LocalDateTime.of(2025, 01, 30, 07, 15, 30), true));
            add(new Message(4, "Ciao! Test", sergio, test, LocalDateTime.of(2025, 01, 31, 07, 15, 30), true));
        }};

    public List<Message> getMessagesList() {
        return messages;
    }

    public void add(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setIsRead(false);
        messages.add(message);
    }

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
