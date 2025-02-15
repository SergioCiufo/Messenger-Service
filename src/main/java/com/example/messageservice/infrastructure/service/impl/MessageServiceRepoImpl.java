package com.example.messageservice.infrastructure.service.impl;

import com.example.messageservice.domain.api.MessageServiceRepo;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.infrastructure.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceRepoImpl  implements MessageServiceRepo {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAllMessages(User user) {
        //return messageRepository.getMessagesByUserSender_IdOrUserReceiver_Id(user.getId(), user.getId());
        //return messageRepository.getMessagesBySenderOrReceiver(user.getUsername(), user.getUsername());
        return messageRepository.findMessagesBySenderAndReceiverIgnoreCase(user.getUsername(), user.getUsername());
    }

    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }
}
