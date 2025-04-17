package com.example.messageservice.application;

import com.example.messageService.generated.application.api.ServizioMessaggisticaApiDelegate;
import com.example.messageService.generated.application.model.*;
import com.example.messageservice.application.mapper.MessangerMappers;
import com.example.messageservice.application.service.AuthServiceFeignImpl;
import com.example.messageservice.application.util.AuthenticationUserUtil;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.*;
import com.example.messageservice.domain.service.MessangerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ServizioMessaggisticaApiDelegateImpl implements ServizioMessaggisticaApiDelegate {

    private final MessangerMappers messangerMappers;
    private final MessangerService messangerService;
    private final AuthenticationUserUtil authenticationUserUtil;
    private final AuthServiceFeignImpl authServiceFeignImpl;

    @Override
    public ResponseEntity<List<RetrieveMessages200ResponseInner>> retrieveMessages() {
        List<User> userList = authServiceFeignImpl.getUsers();
        User userAuth =authenticationUserUtil.getUserAuth();
        List<GetMessageResponse> response = messangerService.getMessage(userAuth, userList);
        List<RetrieveMessages200ResponseInner> messages = response.stream()
                .map(messangerMappers::convertFromDomain)
                .toList();
        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<List<RetrieveUsers200ResponseInner>> retrieveUsers() {
        User userAuth =authenticationUserUtil.getUserAuth();
        log.info("retrieving users");
        try {
            List<User> userList = authServiceFeignImpl.getUsers();
            List<GetUsersResponse> response = messangerService.getUsers(userList, userAuth);
            List<RetrieveUsers200ResponseInner> users = response.stream()
                    .map(messangerMappers::convertFromDomain)
                    .toList();
            return ResponseEntity.ok(users);
        }catch(Exception e){
            log.error("Sending 401 Unauthorized Error");
            return ResponseEntity.status(401).build();
        }
    }

    @Override
    public ResponseEntity <SendMessage200Response> sendMessage(SendMessageRequest sendMessageRequest){
        List<User> userList = authServiceFeignImpl.getUsers();
        User userAuth =authenticationUserUtil.getUserAuth();
        PostMessageRequest request = messangerMappers.convertToDomain(sendMessageRequest);
        PostMessageResponse response = messangerService.sendMessage(request, userAuth, userList);
        SendMessage200Response convertedResponse = messangerMappers.convertFromDomain(response);
        return ResponseEntity.ok(convertedResponse);
    }

    @Override
    public ResponseEntity<List<RetrieveMessages200ResponseInner>> retrieveConversation(RetrieveConversationRequest sendMessageRequest){
        List<User> userList = authServiceFeignImpl.getUsers();
        User userAuth =authenticationUserUtil.getUserAuth();

        GetSingleConversationRequest request = messangerMappers.convertToDomain(sendMessageRequest);
        List<GetSingleConversationResponse> response = messangerService.getSingleConversation(request, userAuth, userList);
        List<RetrieveMessages200ResponseInner> messages = response.stream()
                .map(messangerMappers::convertFromDomain)
                .toList();
        return ResponseEntity.ok(messages);
    }
}
