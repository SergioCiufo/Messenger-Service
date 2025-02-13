package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.api.MessageServiceRepo;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.utill.MessagesListUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessagesService {
    private final MessagesListUtil messagesListUtil;
    private final MessageServiceRepo messageServiceRepo;

    public List<Message> getMessages(User user) {
        return  messageServiceRepo.getAllMessages(user);
    }

    public void sendMessage(User sender, User receiver, String content) {
        Message message = Message.builder()
                .content(content)
                .userSender(sender)
                .userReceiver(receiver)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        messageServiceRepo.sendMessage(message);
    }

    //da spostare in utility
    public String checkNull(String sender, String receiver, String content) {
        return messagesListUtil.checkNull(sender, receiver, content);
    }


}
