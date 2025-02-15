package com.example.messageservice.infrastructure.service.impl;

import com.example.messageservice.domain.api.MessageServiceRepo;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.infrastructure.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceRepoImpl  implements MessageServiceRepo {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAllMessages(User user) {
        return messageRepository.findMessagesBySenderAndReceiver(user.getUsername(), user.getUsername());
    }

    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public List<Message> getSingleConversation(User user, String usernameConversation){
        messageRepository.updateRead(usernameConversation ,user.getUsername());
        return messageRepository.findSingleConversation(user.getUsername(), usernameConversation);
    }
}
