package com.example.messageservice.application;

import com.example.messageService.generated.application.model.*;
import com.example.messageservice.application.mapper.MessangerMappers;
import com.example.messageservice.application.service.AuthServiceFeignImpl;
import com.example.messageservice.application.util.AuthenticationUserUtil;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.*;
import com.example.messageservice.domain.service.MessangerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ServizioMessaggisticaApiDelegateImplTest {
    @InjectMocks
    private ServizioMessaggisticaApiDelegateImpl servizioMessaggisticaApiDelegateImpl;

    @Mock
    private MessangerMappers  messangerMappers;

    @Mock
    private MessangerService messangerService;

    @Mock
    private AuthenticationUserUtil authenticationUserUtil;

    @Mock
    private AuthServiceFeignImpl authServiceFeignImpl;

    @Test
    void shouldRetrieveMessages200ResponseInner_whenAllOk(){
        //PARAMETERS
        User userAuth = new User("authUserTest");
        User user1 = new User("user1Test");
        User user2 = new User("user2Test");
        List<User> userList = List.of(user1, user2);

        GetMessageResponse getMessageResponse =  new GetMessageResponse();
        List<GetMessageResponse> getMessageResponseList = List.of(getMessageResponse);

        RetrieveMessages200ResponseInner retrieveMessages200ResponseInner = new RetrieveMessages200ResponseInner();
        List<RetrieveMessages200ResponseInner> retrieveMessages200ResponseInnerList = List.of(retrieveMessages200ResponseInner);

        //MOCK
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(getMessageResponseList).when(messangerService).getMessage(userAuth, userList);
        doReturn(retrieveMessages200ResponseInner).when(messangerMappers).convertFromDomain(getMessageResponse);
        //TEST
        ResponseEntity<List<RetrieveMessages200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveMessages();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(retrieveMessages200ResponseInnerList, result.getBody());
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(messangerService, times(1)).getMessage(userAuth, userList);
        verify(messangerMappers, times(1)).convertFromDomain(getMessageResponse);
    }

    @Test
    void shouldReturnEmptyList_whenRetrieveMessages200ResponseInnerAuthServiceFails() {
        //PARAMETERS
        User userAuth = new User("authUserTest");

        //MOCK
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(null).when(authServiceFeignImpl).getUsers();

        //TEST
        ResponseEntity<List<RetrieveMessages200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveMessages();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertTrue(result.getBody().isEmpty());
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(authServiceFeignImpl, times(1)).getUsers();
    }

    @Test
    void shouldRetrieveUsers200ResponseInner_whenAllOk() {
        //PARAMETERS
        User userAuth = new User("authUserTest");
        User user1 = new User("user1Test");
        User user2 = new User("user2Test");
        List<User> userList = List.of(user1, user2);

        GetUsersResponse getUsersResponse1 = new GetUsersResponse();
        GetUsersResponse getUsersResponse2 = new GetUsersResponse();
        List<GetUsersResponse> getUsersResponseList = List.of(getUsersResponse1, getUsersResponse2);

        RetrieveUsers200ResponseInner retrieveUsersResponse1 = new RetrieveUsers200ResponseInner();
        RetrieveUsers200ResponseInner retrieveUsersResponse2 = new RetrieveUsers200ResponseInner();
        List<RetrieveUsers200ResponseInner> retrieveUsers200ResponseInnerList = List.of(retrieveUsersResponse1, retrieveUsersResponse2);

        //MOCK
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(getUsersResponseList).when(messangerService).getUsers(userList, userAuth);
        //NON LI PASSA
//        doReturn(retrieveUsersResponse1).when(messangerMappers).convertFromDomain(getUsersResponse1);
//        doReturn(retrieveUsersResponse2).when(messangerMappers).convertFromDomain(getUsersResponse2);
        doReturn(retrieveUsersResponse1, retrieveUsersResponse2)
                .when(messangerMappers)
                .convertFromDomain(any(GetUsersResponse.class));

        //TEST
        ResponseEntity<List<RetrieveUsers200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveUsers();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(retrieveUsers200ResponseInnerList, result.getBody());
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(messangerService, times(1)).getUsers(userList, userAuth);
        verify(messangerMappers, times(2)).convertFromDomain(any(GetUsersResponse.class));

    }

    @Test
    void shouldReturnEmptyList_whenRetrieveUsers200ResponseInnerAuthServiceFails() {
        //PARAMETERS
        User userAuth = new User("authUserTest");

        //MOCK
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(null).when(authServiceFeignImpl).getUsers();

        //TEST
        ResponseEntity<List<RetrieveUsers200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveUsers();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertTrue(result.getBody().isEmpty());
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(authServiceFeignImpl, times(1)).getUsers();
    }

    @Test
    void shouldSendMessage_whenAllOk() {
        //PARAMETERS
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        List<User> userList = List.of(new User("user1"), new User("user2"));
        User userAuth = new User("authUser");
        PostMessageRequest postMessageRequest = new PostMessageRequest();
        PostMessageResponse postMessageResponse = new PostMessageResponse();
        SendMessage200Response sendMessage200Response = new SendMessage200Response();

        //MOCK
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(postMessageRequest).when(messangerMappers).convertToDomain(sendMessageRequest);
        doReturn(postMessageResponse).when(messangerService).sendMessage(postMessageRequest, userAuth, userList);
        doReturn(sendMessage200Response).when(messangerMappers).convertFromDomain(postMessageResponse);

        //TEST
        ResponseEntity<SendMessage200Response> result = servizioMessaggisticaApiDelegateImpl.sendMessage(sendMessageRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(sendMessage200Response, result.getBody());
        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(messangerMappers, times(1)).convertToDomain(sendMessageRequest);
        verify(messangerService, times(1)).sendMessage(postMessageRequest, userAuth, userList);
        verify(messangerMappers, times(1)).convertFromDomain(postMessageResponse);
    }

    @Test
    void shouldThrowRuntimeException_whenSendMessageFails() {
        //PARAMETERS
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        List<User> userList = List.of(new User("user1"), new User("user2"));
        User userAuth = new User("authUser");
        PostMessageRequest postMessageRequest = new PostMessageRequest();

        //MOCK
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(postMessageRequest).when(messangerMappers).convertToDomain(sendMessageRequest);

        doThrow(RuntimeException.class).when(messangerService)
                .sendMessage(postMessageRequest, userAuth, userList);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class,
                () -> servizioMessaggisticaApiDelegateImpl.sendMessage(sendMessageRequest));

        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(messangerMappers, times(1)).convertToDomain(sendMessageRequest);
        verify(messangerService, times(1)).sendMessage(postMessageRequest, userAuth, userList);
    }

    @Test
    void shouldRetrieveConversation_whenAllOk() {
        //PARAMETERS
        RetrieveConversationRequest retrieveConversationRequest = new RetrieveConversationRequest();
        List<User> userList = List.of(new User("user1"), new User("user2"));
        User userAuth = new User("authUser");

        GetSingleConversationRequest getSingleConversationRequest = new GetSingleConversationRequest();
        GetSingleConversationResponse getSingleConversationResponse1 = new GetSingleConversationResponse();
        GetSingleConversationResponse getSingleConversationResponse2 = new GetSingleConversationResponse();
        List<GetSingleConversationResponse> getSingleConversationResponseList = List.of(getSingleConversationResponse1, getSingleConversationResponse2);

        RetrieveMessages200ResponseInner retrieveMessagesResponse1 = new RetrieveMessages200ResponseInner();
        RetrieveMessages200ResponseInner retrieveMessagesResponse2 = new RetrieveMessages200ResponseInner();
        List<RetrieveMessages200ResponseInner> expectedResponse = List.of(retrieveMessagesResponse1, retrieveMessagesResponse2);

        //MOCK
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(getSingleConversationRequest).when(messangerMappers).convertToDomain(retrieveConversationRequest);
        doReturn(getSingleConversationResponseList).when(messangerService).getSingleConversation(getSingleConversationRequest, userAuth, userList);
        doReturn(retrieveMessagesResponse1, retrieveMessagesResponse2)
                .when(messangerMappers)
                .convertFromDomain(any(GetSingleConversationResponse.class));

        //TEST
        ResponseEntity<List<RetrieveMessages200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveConversation(retrieveConversationRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedResponse, result.getBody());
        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(messangerMappers, times(1)).convertToDomain(retrieveConversationRequest);
        verify(messangerService, times(1)).getSingleConversation(getSingleConversationRequest, userAuth, userList);
        verify(messangerMappers, times(2)).convertFromDomain(any(GetSingleConversationResponse.class)); // Ora accetta due chiamate
    }

    @Test
    void shouldThrowRuntimeException_whenRetrieveConversationFails() {
        //PARAMETERS
        RetrieveConversationRequest retrieveConversationRequest = new RetrieveConversationRequest();
        List<User> userList = List.of(new User("user1"), new User("user2"));
        User userAuth = new User("authUser");
        GetSingleConversationRequest getSingleConversationRequest = new GetSingleConversationRequest();

        //MOCK
        doReturn(userList).when(authServiceFeignImpl).getUsers();
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doReturn(getSingleConversationRequest).when(messangerMappers).convertToDomain(retrieveConversationRequest);

        doThrow(RuntimeException.class).when(messangerService)
                .getSingleConversation(getSingleConversationRequest, userAuth, userList);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class,
                () -> servizioMessaggisticaApiDelegateImpl.retrieveConversation(retrieveConversationRequest));

        verify(authServiceFeignImpl, times(1)).getUsers();
        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(messangerMappers, times(1)).convertToDomain(retrieveConversationRequest);
        verify(messangerService, times(1)).getSingleConversation(getSingleConversationRequest, userAuth, userList);
    }

    @Test
    public void shouldReturnUnauthorized_whenExceptionOccurs() {
        //PARAMETERS
        User userAuth = User.builder()
                .username("authUser")
                .build();

        //MOCK
        doReturn(userAuth).when(authenticationUserUtil).getUserAuth();
        doThrow(RuntimeException.class).when(authServiceFeignImpl).getUsers();

        //TEST
        ResponseEntity<List<RetrieveUsers200ResponseInner>> result = servizioMessaggisticaApiDelegateImpl.retrieveUsers();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        Assertions.assertNull(result.getBody());

        verify(authenticationUserUtil, times(1)).getUserAuth();
        verify(authServiceFeignImpl, times(1)).getUsers();
        verifyNoInteractions(messangerService);
        verifyNoInteractions(messangerMappers);
    }


}
