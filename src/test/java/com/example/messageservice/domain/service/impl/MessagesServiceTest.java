package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.repository.MessageServiceRepo;
import com.example.messageservice.domain.util.MessageUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MessagesServiceTest {
    @InjectMocks
    private MessagesService messageService;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private MessageServiceRepo messageServiceRepo;

    @Test
    public void shouldGetListMessages_whenAllOk(){
        //PARAMETERS
        User user = User.builder()
                .username("usernameTest")
                .build();

        Message message1 = Message.builder()
                .content("messageTest")
                .userSender("usernameTest")
                .userReceiver("OsvaldoPaniccia")
                .build();

        Message message2 = Message.builder()
                .content("messageTest")
                .userSender("usernameTest")
                .userReceiver("EnzoSalvi")
                .build();

        List<Message> messagesList = List.of(message1, message2);

        //MOCK
        doReturn(messagesList).when(messageServiceRepo).getAllMessages(user);

        //TEST
        List<Message> result = messageService.getMessages(user);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(messagesList.size(), result.size());
        Assertions.assertEquals(messagesList, result);
        verify(messageServiceRepo, times(1)).getAllMessages(user);
    }

    @Test
    public void shouldThrowException_whenGetMessagesFails(){
        //PARAMETERS
        User user = User.builder()
                .username("usernameTest")
                .build();

        //MOCK
        doThrow(RuntimeException.class).when(messageServiceRepo).getAllMessages(user);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageService.getMessages(user));
    }

    @Test
    public void shouldGetSingleConversation_whenAllOk(){
        //PARAMETERS
        User user = User.builder()
                .username("usernameTest")
                .build();

        String usernameConversation = "OsvaldoPaniccia";

        Message message1 = Message.builder()
                .content("messageTest")
                .userSender(user.getUsername())
                .userReceiver(usernameConversation)
                .build();

        List<Message> messagesList = List.of(message1);

        //MOCK
        doReturn(messagesList).when(messageServiceRepo).getSingleConversation(user, usernameConversation);

        //TEST
        List<Message> result = messageService.getSingleConversation(user, usernameConversation);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(messagesList.size(), result.size());
        Assertions.assertEquals(messagesList, result);
        verify(messageServiceRepo, times(1)).getSingleConversation(user, usernameConversation);
    }

    @Test
    public void shouldThrowException_whenGetSingleConversationFails(){
        //PARAMETERS
        User user = User.builder()
                .username("usernameTest")
                .build();

        String usernameConversation = "EnzoSalvi";

        //MOCK
        doThrow(RuntimeException.class).when(messageServiceRepo).getSingleConversation(user, usernameConversation);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageService.getSingleConversation(user, usernameConversation));
    }

    @Test
    public void shouldSendMessage_whenAllOk(){
        //PARAMETERS
        String sender = "senderTest";
        String receiver = "receiverTest";
        String content = "contentTest";

        LocalDateTime dateTime = LocalDateTime.now();

        Message message = Message.builder()
                .content(content)
                .userSender(sender)
                .userReceiver(receiver)
                .createdAt(dateTime)
                .isRead(false)
                .build();

        //MOCK
        try(MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(dateTime);

        doNothing().when(messageServiceRepo).sendMessage(message);

        //TEST
        messageService.sendMessage(sender, receiver, content);

        //RESULTS
        verify(messageServiceRepo, times(1)).sendMessage(message);
        }
    }

    @Test
    public void shouldThrowException_whenSendMessageFails() {
        //PARAMETERS
        String sender = "senderTest";
        String receiver = "receiverTest";
        String content = "contentTest";

        LocalDateTime dateTime = LocalDateTime.now();

        Message message = Message.builder()
                .content(content)
                .userSender(sender)
                .userReceiver(receiver)
                .createdAt(dateTime)
                .isRead(false)
                .build();

        //MOCK
        try(MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(dateTime);

            doThrow(RuntimeException.class).when(messageServiceRepo).sendMessage(message);

            //TEST + RESULTS
            Assertions.assertThrows(RuntimeException.class, () -> {
                messageService.sendMessage(sender, receiver, content);
            });
        }
    }

    @Test
    public void shouldReturnCheckNull_whenAllOk(){
        //PARAMETERS
        String sender = "senderTest";
        String receiver = "receiverTest";
        String content = null;

        String checkNull = "Content";

        //MOCK
        doReturn(checkNull).when(messageUtil).checkNull(sender, receiver, content);

        //TEST
        String result = messageService.checkNull(sender, receiver, content);

        //RESULTS
        Assertions.assertEquals(checkNull, result);
        verify(messageUtil, times(1)).checkNull(sender, receiver, content);
    }

    @Test
    public void shouldThrowException_whenCheckNullFails(){
        //PARAMETERS
        String sender = "senderTest";
        String receiver = "receiverTest";
        String content = null;

        String checkNull = "Content";

        //MOCK
        doThrow(RuntimeException.class).when(messageUtil).checkNull(sender, receiver, content);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageService.checkNull(sender, receiver, content));

    }

}
