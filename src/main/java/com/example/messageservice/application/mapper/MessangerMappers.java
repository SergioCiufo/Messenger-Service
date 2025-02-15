package com.example.messageservice.application.mapper;

import com.example.messageService.generated.application.model.*;
import com.example.messageservice.domain.model.messanger.*;
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
    RetrieveMessages200ResponseInner convertFromDomain(GetMessageResponse response);

    //POSTCONVERSATION
    GetSingleConversationRequest convertToDomain(RetrieveConversationRequest request);
    @Mapping(target = "usernameSender", source = "response.username_sender")
    @Mapping(target = "usernameReceiver", source = "response.username_receiver")
    @Mapping(target = "createdAt", source = "response.createdAt", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(target= "isRead", source = "response.isRead")
    RetrieveMessages200ResponseInner convertFromDomain(GetSingleConversationResponse response);

    //GETUSERS
    @Mapping(target = "username", source = "response.username")
    RetrieveUsers200ResponseInner convertFromDomain(GetUsersResponse response);

    //SENDMESSAGE
    PostMessageRequest convertToDomain(SendMessageRequest request);
    SendMessage200Response convertFromDomain(PostMessageResponse response);



    //metodo per convertire la stringa della data ricevuta in OffSetDateTime
    @Named("stringToOffsetDateTime")
    default OffsetDateTime stringToOffsetDateTime(String dateString) {
        return dateString != null ? OffsetDateTime.parse(dateString) : null;
    }

}
