package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.exception.EmptyFieldException;
import com.example.messageservice.domain.exception.NotFoundException;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.GetMessageResponse;
import com.example.messageservice.domain.model.messanger.GetUsersResponse;
import com.example.messageservice.domain.model.messanger.PostMessageRequest;
import com.example.messageservice.domain.model.messanger.PostMessageResponse;
import com.example.messageservice.domain.service.MessangerService;
import com.example.messageservice.domain.utill.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class MessangerServiceImpl implements MessangerService {

    private final MessagesService messagesService;
    private final LocalDateTimeUtil localDateTimeUtil;
    //private final UserService userService;

    @Override
    @Transactional
    public List<GetMessageResponse> getMessage(User userAuth, List<User> userList) {

//          OLD
//        String username = userAuth.getUsername();
//        User user = userService.getUserByUsername(username);
//        List<Message> messagesList = messagesService.getMessages(user);

        userList.stream()
                .filter(user -> user.getUsername().equals(userAuth.getUsername()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found with username: " + userAuth.getUsername()));

        List<Message> messagesList = messagesService.getMessages(userAuth);

        List<GetMessageResponse> responseList = messagesList.stream()
                .map(message -> GetMessageResponse.builder()
                        .id(String.valueOf(message.getId()))
                        .content(message.getContent())
                        //.username_sender(message.getUserSender().getUsername())
                        .username_sender(message.getUserSender())
                        //.username_receiver(message.getUserReceiver().getUsername())
                        .username_receiver(message.getUserReceiver())
                        .createdAt(localDateTimeUtil.formatCreatedAt(message.getCreatedAt()))
                        .isRead(message.getIsRead())
                        .build())
                .toList();

        return responseList;
    }

    @Override
    @Transactional
    public List<GetUsersResponse> getUsers(List<User> userList ,User userAuth) {

        User userToRemove = userList.stream()
                .filter(user -> user.getUsername().equals(userAuth.getUsername()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found with username: " + userAuth.getUsername()));

        userList.remove(userToRemove);


        List<GetUsersResponse> responseList = userList.stream()
                    .map(user -> GetUsersResponse.builder()
                            .username(user.getUsername())
                            .build())
                    .toList();
            return responseList;

//            OLD
//        String username = userAuth.getUsername();
//
//        User userClient = userService.getUserByUsername(username);
//        List<User> usersList = userService.getAllUsersExceptUser(userClient);
//
//        List<GetUsersResponse> responseList = usersList.stream()
//                .map(user -> GetUsersResponse.builder()
//                        .idUser(String.valueOf(user.getId()))
//                        .username(user.getUsername())
//                        .build())
//                .toList();
//
//        return responseList;
    }


    @Override
    @Transactional
    public PostMessageResponse sendMessage(PostMessageRequest request, User userAuth, List<User> userList) {
        String usernameSender = request.getUsernameSender();
        String usernameReceiver = request.getUsernameReceiver();
        String content = request.getContent();

        userList.stream()
                .filter(user -> user.getUsername().equals(userAuth.getUsername()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found with username: " + userAuth.getUsername()));

        userList.stream()
                .filter(user -> user.getUsername().equals(usernameReceiver))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found with username: " + usernameReceiver));

        String nullCheck = messagesService.checkNull(usernameSender, usernameReceiver, content);
        if(!nullCheck.isEmpty()) {
            throw new EmptyFieldException("Campo/i vuoti: " + nullCheck);
        }

        messagesService.sendMessage(usernameSender, usernameReceiver, content);

        return PostMessageResponse.builder()
                .message("Messaggio inviato")
                .build();

//        OLD
//        String usernameSender = userAuth.getUsername();
//        //String usernameSender = request.getUsernameSender();
//        String usernameReceiver = request.getUsernameReceiver();
//        String content = request.getContent();
//
//        String nullCheck = messagesService.checkNull(usernameSender, usernameReceiver, content);
//        if(!nullCheck.isEmpty()) {
//            throw new EmptyFieldException("Campo/i vuoti: " + nullCheck);
//        }
//
//        User sender = userService.getUserByUsername(usernameSender);
//        User receiver  = userService.getUserByUsername(usernameReceiver);
//
//        messagesService.sendMessage(sender, receiver, content);
//
//        return PostMessageResponse.builder()
//                .message("Messaggio inviato")
//                .build();
    }


}
