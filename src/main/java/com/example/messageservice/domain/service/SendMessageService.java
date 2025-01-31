package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.utill.MessagesListUtil;
import com.example.messageservice.domain.utill.UserListUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMessageService {
    private final UserListUtil userListUtil;
    private final MessagesListUtil messagesListUtil;

    public void sendMessage(String sender, String receiver, String content) {

        User senderUser = userListUtil.getUserByUsername(sender);
        User receiverUser = userListUtil.getUserByUsername(receiver);

        Message message = Message.builder()
                .userSender(senderUser)
                .userReceiver(receiverUser)
                .content(content)
                .build();

        messagesListUtil.add(message);
    }
}
