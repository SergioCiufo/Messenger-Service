package com.example.messageservice.application;

import com.example.messageService.generated.application.api.ServizioMessaggisticaApiDelegate;
import com.example.messageService.generated.application.model.RetrieveMessages200ResponseInner;
import com.example.messageService.generated.application.model.RetrieveUsers200ResponseInner;
import com.example.messageService.generated.application.model.SendMessage200Response;
import com.example.messageService.generated.application.model.SendMessageRequest;
import com.example.messageservice.application.mapper.MessangerMappers;
import com.example.messageservice.domain.model.messanger.GetMessageResponse;
import com.example.messageservice.domain.model.messanger.GetUsersResponse;
import com.example.messageservice.domain.model.messanger.SendMessageResponse;
import com.example.messageservice.domain.service.MessangerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ServizioMessaggisticaApiDelegateImpl implements ServizioMessaggisticaApiDelegate {

    private final MessangerMappers messangerMappers;
    private final MessangerService messangerService;

    @Override
    public ResponseEntity<List<RetrieveMessages200ResponseInner>> retrieveMessages() {
        List<GetMessageResponse> response = messangerService.getMessage();
        List<RetrieveMessages200ResponseInner> messages = response.stream()
                .map(messangerMappers::convertFromDomain)
                .toList();
        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<List<RetrieveUsers200ResponseInner>> retrieveUsers() {
        List<GetUsersResponse> response = messangerService.getUsers();
        List<RetrieveUsers200ResponseInner> users = response.stream()
                .map(messangerMappers::convertFromDomain)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity <SendMessage200Response> sendMessage(SendMessageRequest sendMessageRequest){
        com.example.messageservice.domain.model.messanger.SendMessageRequest request = messangerMappers.convertToDomain(sendMessageRequest);
        SendMessageResponse response = messangerService.sendMessage(request);
        SendMessage200Response convertedResponse = messangerMappers.convertFromDomain(response);
        return ResponseEntity.ok(convertedResponse);
    }
}
