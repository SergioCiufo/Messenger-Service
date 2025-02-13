package com.example.messageservice.domain.api;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;

import java.util.List;

public interface MessageServiceRepo {
    List<Message> getAllMessages(User user);
    void sendMessage(Message message);
}
