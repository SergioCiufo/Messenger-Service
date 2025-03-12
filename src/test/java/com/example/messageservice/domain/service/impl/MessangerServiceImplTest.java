package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.exception.EmptyFieldException;
import com.example.messageservice.domain.exception.NotFoundException;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.*;
import com.example.messageservice.domain.util.LocalDateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MessangerServiceImplTest {

    @InjectMocks
    private MessangerServiceImpl messangerServiceImpl;

    @Mock
    private MessagesService messagesService;

    @Mock
    private LocalDateTimeUtil localDateTimeUtil;

    @Test
    public void shouldGetMessages_whenAllOk() {
        //PARAMETERS
        String usernameAuth = "usernameAuthTest";
        String username1 = "usernameTest1";

        User userAuth = User.builder()
                .username(usernameAuth)
                .build();

        User user1 = User.builder()
                .username(username1)
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        Message message1 = Message.builder()
                .content("messageTest")
                .userSender("usernameAuthTest")
                .userReceiver("usernameTest1")
                .isRead(false)
                .build();

        List<Message> messageList = Arrays.asList(message1);

        LocalDateTime localDateTime = null;
        String formattedDate = "defaultDate";

        //MOCK
        doReturn(messageList).when(messagesService).getMessages(userAuth);
        doReturn(formattedDate).when(localDateTimeUtil).formatCreatedAt(localDateTime);

        //TEST
        List<GetMessageResponse> result = messangerServiceImpl.getMessage(userAuth, userList);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("usernameAuthTest", result.get(0).getUsername_sender());
        Assertions.assertEquals("usernameTest1", result.get(0).getUsername_receiver());
        Assertions.assertEquals("messageTest", result.get(0).getContent());
        Assertions.assertFalse(result.get(0).getIsRead());
        Assertions.assertEquals(formattedDate, result.get(0).getCreatedAt());
    }

    @Test
    public void shouldGetMessagesThrowException_whenUserAuthNotInList() {
        //PARAMETERS
        String usernameAuth = "usernameAuthTest";
        String username1 = "usernameTest1";

        User userAuth = User.builder()
                .username(usernameAuth)
                .build();

        User user1 = User.builder()
                .username(username1)
                .build();

        List<User> userList = Arrays.asList(user1);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> {
            messangerServiceImpl.getMessage(userAuth, userList);
        });
    }

    @Test
    public void shouldGetUsers_whenAllOk() {
        //PARAMETERS
        User userAuth = User.builder()
                .username("usernameAuth")
                .build();

        User user1 = User.builder()
                .username("username1")
                .build();

        User user2 = User.builder()
                .username("username2")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(userAuth);

        //TEST
        List<GetUsersResponse> result = messangerServiceImpl.getUsers(userList, userAuth);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().noneMatch(user -> user.getUsername().equals(userAuth.getUsername())));
    }

    @Test
    public void shouldGetUsersThrowException_whenUserAuthNotFound() {
        //PARAMETERS
        User userAuth = User.builder()
                .username("usernameAuth")
                .build();

        User user1 = User.builder()
                .username("username1")
                .build();

        User user2 = User.builder()
                .username("username2")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> {
            messangerServiceImpl.getUsers(userList, userAuth);
        });
    }

    @Test
    public void shouldSendMessage_whenAllOk() {
        //PARAMETERS
        String sender = "usernameAuth";
        String receiver = "username1";
        String content = "contentTest";

        User userAuth = User.builder()
                .username("usernameAuth")
                .build();

        User user1 = User.builder()
                .username("username1")
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        PostMessageRequest request = PostMessageRequest.builder()
                .usernameSender("usernameAuth")
                .usernameReceiver("username1")
                .content("contentTest")
                .build();

        //MOCK
        doReturn("").when(messagesService).checkNull(receiver, sender, content);
        doNothing().when(messagesService).sendMessage(receiver, sender, content);

        //TEST
        PostMessageResponse result = messangerServiceImpl.sendMessage(request, userAuth, userList);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Messaggio inviato", result.getMessage());
        verify(messagesService).checkNull(receiver, sender, content);
        verify(messagesService).sendMessage(receiver, sender, content);
    }

    @Test
    public void shouldSendMessageThrowNotFoundException_whenUserNotFound() {
        //PARAMETERS
        User userAuth = User.builder()
                .username("usernameAuth")
                .build();


        PostMessageRequest request = PostMessageRequest.builder()
                .usernameSender("usernameAuth")
                .usernameReceiver("username1")
                .content("contentTest")
                .build();

        List<User> userList = Arrays.asList(userAuth);

        //TEST
        Assertions.assertThrows(NotFoundException.class, () -> {
            messangerServiceImpl.sendMessage(request, userAuth, userList);
        });
    }

    @Test
    public void shouldSendMessageThrowEmptyFieldException_whenNullCheckIsNotEmpty() {
        //PARAMETERS
        String sender = "usernameAuth";
        String receiver = "username1";
        String content = "contentTest";

        User userAuth = User.builder()
                .username("usernameAuth")
                .build();

        User user1 = User.builder()
                .username("username1")
                .build();

        PostMessageRequest request = PostMessageRequest.builder()
                .usernameSender("usernameAuth")
                .usernameReceiver("username1")
                .content("contentTest")
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        //MOCK
        doReturn("Content").when(messagesService).checkNull(receiver, sender, content);

        //TEST
        Assertions.assertThrows(EmptyFieldException.class, () -> {
            messangerServiceImpl.sendMessage(request, userAuth, userList);
        });
        verify(messagesService).checkNull(receiver, sender, content);
    }



    @Test
    public void shouldSendMessageThrowException_whenSendMessageFails() {
        //PARAMETERS
        String sender = "usernameAuth";
        String receiver = "username1";
        String content = "contentTest";

        User userAuth = User.builder()
                .username("usernameAuth")
                .build();

        User user1 = User.builder()
                .username("username1")
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        PostMessageRequest request = PostMessageRequest.builder()
                .usernameSender("usernameAuth")
                .usernameReceiver("username1")
                .content("contentTest")
                .build();

        //MOCK
        doReturn("").when(messagesService).checkNull(receiver, sender, content);

        doThrow(RuntimeException.class).when(messagesService).sendMessage(receiver, sender, content);

        //TEST
        Assertions.assertThrows(RuntimeException.class, () -> {
            messangerServiceImpl.sendMessage(request, userAuth, userList);
        });

        //RESULTS
        verify(messagesService).checkNull(receiver, sender, content);
        verify(messagesService).sendMessage(receiver, sender, content);
    }

    @Test
    public void shouldGetSingleConversation_whenAllOk() {
        //PARAMETERS
        GetSingleConversationRequest request = GetSingleConversationRequest.builder()
                .usernameConversation(null)
                .build();

        User userAuth = User.builder()
                .username("usernameAuthTest")
                .build();

        User user1 = User.builder()
                .username("usernameTest1")
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        //TEST
        Assertions.assertThrows(NotFoundException.class, () -> {
            messangerServiceImpl.getSingleConversation(request, userAuth, userList);
        });
    }

    @Test
    public void shouldGetSingleConversationThrowNotFoundException_whenUserNotFound() {
        //PARAMETERS
        GetSingleConversationRequest request = GetSingleConversationRequest.builder()
                .usernameConversation("usernameTest1")
                .build();

        User userAuth = User.builder()
                .username("usernameAuthTest")
                .build();

        User user1 = User.builder()
                .username("usernameTest1")
                .build();

        List<User> userList = Arrays.asList(userAuth, user1);

        Message message = Message.builder()
                .content("messageTest")
                .userSender("usernameAuthTest")
                .userReceiver("usernameTest1")
                .isRead(false)
                .build();

        List<Message> messageList = Arrays.asList(message);

        LocalDateTime localDateTime = null;
        String formattedDate = "defaultDate";

        //MOCK
        doReturn(messageList).when(messagesService).getSingleConversation(userAuth, request.getUsernameConversation());
        doReturn(formattedDate).when(localDateTimeUtil).formatCreatedAt(localDateTime);

        //TEST
        List<GetSingleConversationResponse> result = messangerServiceImpl.getSingleConversation(request, userAuth, userList);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("usernameAuthTest", result.get(0).getUsername_sender());
        Assertions.assertEquals("usernameTest1", result.get(0).getUsername_receiver());
        Assertions.assertEquals("messageTest", result.get(0).getContent());
        Assertions.assertFalse(result.get(0).getIsRead());
        Assertions.assertEquals(formattedDate, result.get(0).getCreatedAt());
    }
}
