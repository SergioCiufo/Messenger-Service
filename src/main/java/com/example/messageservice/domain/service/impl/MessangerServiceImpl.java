package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.exception.EmptyFieldException;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.GetMessageResponse;
import com.example.messageservice.domain.model.messanger.GetUsersResponse;
import com.example.messageservice.domain.model.messanger.SendMessageRequest;
import com.example.messageservice.domain.model.messanger.SendMessageResponse;
import com.example.messageservice.domain.service.MessangerService;
import com.example.messageservice.domain.service.MessagesService;
import com.example.messageservice.domain.service.SendMessageService;
import com.example.messageservice.domain.service.UserService;
import com.example.messageservice.domain.utill.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class MessangerServiceImpl implements MessangerService {

    private final MessagesService messagesService;
    private final LocalDateTimeUtil localDateTimeUtil;
    private final UserService userService;
    private final SendMessageService sendMessageService;

    @Override
    public List<GetMessageResponse> getMessageFirstStep() {
        String username = "test"; //provvisorio
        //cercare l'utente by username dalla lista utenti
        //se corrisponde usare l'id utente per trovare la lista messaggi dedicata
        if(username == null) {
            log.error("Username is null");
            throw new IllegalArgumentException("Username is null");
        }

        List<Message> messagesList = messagesService.getMessagesList(username);

        List<GetMessageResponse> responseList = messagesList.stream()
                .map(message -> GetMessageResponse.builder()
                        .id(String.valueOf(message.getId()))
                        .content(message.getContent())
                        .username_sender(message.getUserSender().getUsername())
                        .username_receiver(message.getUserReceiver().getUsername())
                        .createdAt(localDateTimeUtil.formatCreatedAt(message.getCreatedAt()))
                        .isRead(message.getIsRead())
                        .build())
                .toList();

        return responseList;
    }

    @Override
    public List<GetUsersResponse> getUsersFirstStep() {
        List<User> usersList = userService.getUsersList();

        List<GetUsersResponse> responseList = usersList.stream()
                .map(user -> GetUsersResponse.builder()
                        .idUser(String.valueOf(user.getId()))
                        .username(user.getUsername())
                        .build())
                .toList();

        return responseList;
    }

    @Override
    public SendMessageResponse sendMessageFirstStep(SendMessageRequest request) {
        String sender = request.getUsernameSender();
        String receiver = request.getUsernameReceiver();
        String content = request.getContent();

        String nullCheck = messagesService.checkNull(sender, receiver, content);
        if(!nullCheck.isEmpty()) {
            throw new EmptyFieldException("Campo/i vuoti: " + nullCheck);
        }

        sendMessageService.sendMessage(sender, receiver, content);

        return SendMessageResponse.builder()
                .message("Messaggio inviato")
                .build();
    }


}
