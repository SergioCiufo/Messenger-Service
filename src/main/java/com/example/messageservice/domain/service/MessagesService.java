package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.utill.MessagesListUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessagesService {
    private final MessagesListUtil messagesListUtil;

    public List<Message> getMessagesList(String username) {
        return messagesListUtil.getMessagesList().stream()
                .filter(message -> message.getUserSender().getUsername().equals(username)
                        || message.getUserReceiver().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public String checkNull(String sender, String receiver, String content) {
        return messagesListUtil.checkNull(sender, receiver, content);
    }


}
