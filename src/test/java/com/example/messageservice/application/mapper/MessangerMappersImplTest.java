package com.example.messageservice.application.mapper;
/*
import com.example.messageService.generated.application.model.*;
import com.example.messageservice.application.util.DateTimeUtil;
import com.example.messageservice.domain.model.messanger.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
public class MessangerMappersImplTest {
    @InjectMocks
    private MessangerMappersImpl  messangerMappersImpl;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Test
    void shouldConvertFromDomainRetrieveMessages200ResponseInner_whenAllOk(){
        //PARAMETERS
        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setUsername_sender("usernameSenderTest");
        getMessageResponse.setUsername_receiver("usernameReceiverTest");
        getMessageResponse.setCreatedAt("2020-04-20T00:00:00+00:00");
        getMessageResponse.setIsRead(false);
        getMessageResponse.setId("12345");
        getMessageResponse.setContent("contentTest");

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(getMessageResponse.getCreatedAt());

        //MOCK
        when(dateTimeUtil.stringToOffsetDateTime(getMessageResponse.getCreatedAt())).thenReturn(offsetDateTime);

        //TEST
        RetrieveMessages200ResponseInner result = messangerMappersImpl.convertFromDomain(getMessageResponse);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(getMessageResponse.getUsername_sender(), result.getUsernameSender());
        Assertions.assertEquals(getMessageResponse.getUsername_receiver(), result.getUsernameReceiver());
        Assertions.assertEquals(offsetDateTime, result.getCreatedAt());
        Assertions.assertEquals(getMessageResponse.getIsRead(), result.getIsRead());
        Assertions.assertEquals(getMessageResponse.getId(), result.getId());
        Assertions.assertEquals(getMessageResponse.getContent(), result.getContent());
        verify(dateTimeUtil, times(1)).stringToOffsetDateTime(getMessageResponse.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionConvertFromDomainRetrieveMessages200ResponseInner_whenDateIsInvalid(){
        //PARAMETERS
        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setUsername_sender("usernameSenderTest");
        getMessageResponse.setUsername_receiver("usernameReceiverTest");
        getMessageResponse.setCreatedAt("invalidDate");
        getMessageResponse.setIsRead(false);
        getMessageResponse.setId("12345");
        getMessageResponse.setContent("contentTest");

        //MOCK
        when(dateTimeUtil.stringToOffsetDateTime(getMessageResponse.getCreatedAt()))
                .thenThrow(new RuntimeException());

        //TEST
        Assertions.assertThrows(RuntimeException.class, () -> {
            messangerMappersImpl.convertFromDomain(getMessageResponse);
        });

        //RESULTS
        verify(dateTimeUtil, times(1)).stringToOffsetDateTime(getMessageResponse.getCreatedAt());
    }

    @Test
    void shouldReturnNull_whenConvertFromDomainRetrieveMessages200ResponseInnerIsNull(){
        //PARAMETERS
        GetMessageResponse getMessageResponse = null;

        //TEST
        RetrieveMessages200ResponseInner result = messangerMappersImpl.convertFromDomain(getMessageResponse);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldConvertToDomainGetSingleConversationRequest_whenAllOk(){
        //PARAMETERS
        RetrieveConversationRequest retrieveConversationRequest = new RetrieveConversationRequest();
        retrieveConversationRequest.setUsernameConversation("usernameConversationTest");

        //TEST
        GetSingleConversationRequest result =  messangerMappersImpl.convertToDomain(retrieveConversationRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(retrieveConversationRequest.getUsernameConversation(), result.getUsernameConversation());
    }

    @Test
    void shouldReturnNull_whenConvertToDomainGetSingleConversationRequestIsNull(){
        //PARAMETERS
        RetrieveConversationRequest retrieveConversationRequest = null;

        //TEST
        GetSingleConversationRequest result = messangerMappersImpl.convertToDomain(retrieveConversationRequest);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldConvertFromDomainRetrieveMessages200ResponseInnerSingleConversation_whenAllOk(){
        GetSingleConversationResponse getSingleConversationResponse = new GetSingleConversationResponse();
        getSingleConversationResponse.setUsername_sender("usernameSenderTest");
        getSingleConversationResponse.setUsername_receiver("usernameReceiverTest");
        getSingleConversationResponse.setCreatedAt("2020-04-20T00:00:00+00:00");
        getSingleConversationResponse.setIsRead(false);
        getSingleConversationResponse.setId("12345");
        getSingleConversationResponse.setContent("contentTest");

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(getSingleConversationResponse.getCreatedAt());

        when(dateTimeUtil.stringToOffsetDateTime(getSingleConversationResponse.getCreatedAt())).thenReturn(offsetDateTime);

        RetrieveMessages200ResponseInner result = messangerMappersImpl.convertFromDomain(getSingleConversationResponse);


        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(getSingleConversationResponse.getUsername_sender(), result.getUsernameSender());
        Assertions.assertEquals(getSingleConversationResponse.getUsername_receiver(), result.getUsernameReceiver());
        Assertions.assertEquals(offsetDateTime, result.getCreatedAt());
        Assertions.assertEquals(getSingleConversationResponse.getIsRead(), result.getIsRead());
        Assertions.assertEquals(getSingleConversationResponse.getId(), result.getId());
        Assertions.assertEquals(getSingleConversationResponse.getContent(), result.getContent());
        verify(dateTimeUtil, times(1)).stringToOffsetDateTime(getSingleConversationResponse.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionConvertFromDomainRetrieveMessages200ResponseInnerSingleConversation_whenDateIsInvalid(){
        //PARAMETERS
        GetSingleConversationResponse getSingleConversationResponse = new GetSingleConversationResponse();
        getSingleConversationResponse.setUsername_sender("usernameSenderTest");
        getSingleConversationResponse.setUsername_receiver("usernameReceiverTest");
        getSingleConversationResponse.setCreatedAt("invalidDate");
        getSingleConversationResponse.setIsRead(false);
        getSingleConversationResponse.setId("12345");
        getSingleConversationResponse.setContent("contentTest");

        //MOCK
        when(dateTimeUtil.stringToOffsetDateTime(getSingleConversationResponse.getCreatedAt()))
                .thenThrow(new RuntimeException());

        //TEST
        Assertions.assertThrows(RuntimeException.class, () -> {
            messangerMappersImpl.convertFromDomain(getSingleConversationResponse);
        });

        //RESULTS
        verify(dateTimeUtil, times(1)).stringToOffsetDateTime(getSingleConversationResponse.getCreatedAt());
    }

    @Test
    void shouldReturnNull_whenConvertFromDomainRetrieveMessages200ResponseInnerSingleConversationIsNull(){
        //PARAMETERS
        GetSingleConversationResponse getSingleConversationResponse = null;

        //TEST
        RetrieveMessages200ResponseInner result = messangerMappersImpl.convertFromDomain(getSingleConversationResponse);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldConvertFromDomainRetrieveUsers200ResponseInner_whenAllOk(){
        //PARAMETERS
        GetUsersResponse getUsersResponse = new GetUsersResponse();
        getUsersResponse.setUsername("usernameTest");

        //TEST
        RetrieveUsers200ResponseInner result = messangerMappersImpl.convertFromDomain(getUsersResponse);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(getUsersResponse.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnNull_whenConvertFromDomainRetrieveUsers200ResponseInnerisNull(){
        //PARAMETERS
        GetUsersResponse getUsersResponse = null;

        //TEST
        RetrieveUsers200ResponseInner result = messangerMappersImpl.convertFromDomain(getUsersResponse);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldConvertToDomainPostMessageRequest_whenAllOk(){
        //PARAMETERS
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setUsernameSender("usernameSenderTest");
        sendMessageRequest.setUsernameReceiver("usernameReceiverTest");
        sendMessageRequest.setContent("contentTest");

        //TEST
        PostMessageRequest result =  messangerMappersImpl.convertToDomain(sendMessageRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sendMessageRequest.getUsernameSender(), result.getUsernameSender());
        Assertions.assertEquals(sendMessageRequest.getUsernameReceiver(), result.getUsernameReceiver());
        Assertions.assertEquals(sendMessageRequest.getContent(), result.getContent());
    }

    @Test
    void shouldReturnNull_whenConvertToDomainPostMessageRequestIsNull(){
        //PARAMETERS
        SendMessageRequest sendMessageRequest = null;

        //TEST
        PostMessageRequest result = messangerMappersImpl.convertToDomain(sendMessageRequest);

        //RESULTS
        Assertions.assertNull(result);
    }

    @Test
    void shouldConvertFromDomainSendMessage200Response_whenAllOk(){
        //PARAMETERS
        PostMessageResponse postMessageResponse = new PostMessageResponse();
        postMessageResponse.setMessage("messageTest");

        //TEST
        SendMessage200Response result = messangerMappersImpl.convertFromDomain(postMessageResponse);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(postMessageResponse.getMessage(), result.getMessage());
    }

    @Test
    void shouldReturnNull_whenConvertFromDomainSendMessage200ResponseIsNull(){
        //PARAMETERS
        PostMessageResponse postMessageResponse = null;

        //TEST
        SendMessage200Response result = messangerMappersImpl.convertFromDomain(postMessageResponse);

        //RESULTS
        Assertions.assertNull(result);
    }
}
*/