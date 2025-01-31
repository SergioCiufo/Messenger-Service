package com.example.messageservice.application.mapper;

import com.example.messageService.generated.application.model.RetrieveMessages200ResponseInner;
import com.example.messageService.generated.application.model.RetrieveUsers200ResponseInner;
import com.example.messageService.generated.application.model.SendMessage200Response;
import com.example.messageService.generated.application.model.SendMessageRequest;
import com.example.messageservice.domain.model.messanger.FirstStepGetMessageResponse;
import com.example.messageservice.domain.model.messanger.FirstStepGetUsersResponse;
import com.example.messageservice.domain.model.messanger.FirstStepSendMessageRequest;
import com.example.messageservice.domain.model.messanger.FirstStepSendMessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.OffsetDateTime;

@Mapper
public interface MessangerMappers {

    //GETMESSAGES
    @Mapping(target = "usernameSender", source = "response.username_sender")
    @Mapping(target = "usernameReceiver", source = "response.username_receiver")
    @Mapping(target = "createdAt", source = "response.createdAt", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(target= "isRead", source = "response.isRead")
    RetrieveMessages200ResponseInner convertFromDomain(FirstStepGetMessageResponse response);

    //metodo per convertire la stringa della data ricevuta in OffSetDateTime
    @Named("stringToOffsetDateTime")
    default OffsetDateTime stringToOffsetDateTime(String dateString) {
        return dateString != null ? OffsetDateTime.parse(dateString) : null;
    }

    //GETUSERS
    @Mapping(target = "idUser", source = "response.idUser")
    @Mapping(target = "username", source = "response.username")
    RetrieveUsers200ResponseInner convertFromDomain(FirstStepGetUsersResponse response);

    //SENDMESSAGE
    FirstStepSendMessageRequest convertToDomain(SendMessageRequest request);
    SendMessage200Response convertFromDomain(FirstStepSendMessageResponse response);



}
