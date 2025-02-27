package com.example.messageservice.infrastructure.service.impl;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.infrastructure.repository.MessageRepository;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceRepoImplTest {

    @InjectMocks
    private MessageServiceRepoImpl messageServiceRepoImpl;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void shouldGetAllMessages_whenAllOk(){
        //PARAMETERS
        User user = new User();
        user.setUsername("testUsername");

        Message message1 = new Message();
        message1.setUserSender(user.getUsername());
        Message message2 = new Message();
        message2.setUserReceiver(user.getUsername());
        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        //MOCK
        doReturn(messageList).when(messageRepository).findMessagesBySenderAndReceiver(user.getUsername(),  user.getUsername());

        //TEST
        List<Message> result = messageServiceRepoImpl.getAllMessages(user);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("testUsername", result.get(0).getUserSender());
        Assertions.assertEquals("testUsername", result.get(1).getUserReceiver());
        verify(messageRepository, times(1)).findMessagesBySenderAndReceiver(user.getUsername(), user.getUsername());
    }

    @Test
    void shouldGetEmptyList_whenGetAllMessagesReturnEmptyList(){
        //PARAMETERS
        User user = new User();
        user.setUsername("testUsername");

        //MOCK
        doReturn(Collections.emptyList()).when(messageRepository).findMessagesBySenderAndReceiver(user.getUsername(),  user.getUsername());

        //TEST
        List<Message> result = messageServiceRepoImpl.getAllMessages(user);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowException_whenGetAllMessagesFails(){
        User user = new User();
        user.setUsername("testUsername");

        //MOCK
        doThrow(RuntimeException.class).when(messageRepository).findMessagesBySenderAndReceiver(user.getUsername(),  user.getUsername());

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageServiceRepoImpl.getAllMessages(user));
    }

    @Test
    void shouldSendMessage_whenAllOk(){
        //PARAMETERS
        User userSender = new User();
        userSender.setUsername("testUsernameSender");
        User userReceiver = new User();
        userReceiver.setUsername("testUsernameReceiver");

        Message message = new Message();
        message.setUserSender(userSender.getUsername());
        message.setUserReceiver(userReceiver.getUsername());
        message.setContent("testContent");

        //MOCK
        doReturn(message).when(messageRepository).save(message);

        //TEST
        messageServiceRepoImpl.sendMessage(message);

        //RESULTS
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void shouldThrowException_whenSendMessageFails(){
        //PARAMETERS
        User userSender = new User();
        userSender.setUsername("testUsernameSender");
        User userReceiver = new User();
        userReceiver.setUsername("testUsernameReceiver");
        Message message = new Message();
        message.setUserSender(userSender.getUsername());
        message.setUserReceiver(userReceiver.getUsername());
        message.setContent("testContent");

        //MOCK
        doThrow(RuntimeException.class).when(messageRepository).save(message);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageServiceRepoImpl.sendMessage(message));
    }

    @Test
    void shouldGetSingleConversation_whenAllOk(){
        //PARAMETERS
        User userReceiver = new User();
        userReceiver.setUsername("testUsernameReceiver");
        String usernameSender = "testUsernameSender";

        Message message1 = new Message();
        message1.setUserSender(usernameSender);
        message1.setUserReceiver(userReceiver.getUsername());
        message1.setContent("testContent1");
        message1.setIsRead(false);

        Message message2 = new Message();
        message2.setUserSender(usernameSender);
        message2.setUserReceiver(userReceiver.getUsername());
        message2.setContent("testContent2");
        message2.setIsRead(false);

        List<Message> messageList = new  ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        Query expectedQuery = new Query(Criteria.where("userSender").is(usernameSender)
                .and("userReceiver").is(userReceiver.getUsername()));
        Update expectedUpdate = new Update().set("isRead", true);

        //MOCK
        UpdateResult updateResult = mock(UpdateResult.class); //UpdateResult per simulare l'update
        doReturn(updateResult).when(mongoTemplate).updateMulti(expectedQuery, expectedUpdate, Message.class);
        doReturn(messageList).when(messageRepository).findSingleConversation(userReceiver.getUsername(), usernameSender);

        //TEST
        List<Message> result = messageServiceRepoImpl.getSingleConversation(userReceiver, usernameSender);

        //VERIFY
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(usernameSender, result.get(0).getUserSender());
        Assertions.assertEquals(userReceiver.getUsername(), result.get(1).getUserReceiver());

        verify(mongoTemplate, times(1)).updateMulti(expectedQuery, expectedUpdate, Message.class);
        verify(messageRepository, times(1)).findSingleConversation(userReceiver.getUsername(), usernameSender);
    }

    @Test
    void shouldThrowException_whenGetSingleConversationFails(){
        //PARAMETERS
        User userReceiver = new User();
        userReceiver.setUsername("testUsernameReceiver");
        String usernameSender = "testUsernameSender";

        Query expectedQuery = new Query(Criteria.where("userSender").is(usernameSender)
                .and("userReceiver").is(userReceiver.getUsername()));
        Update expectedUpdate = new Update().set("isRead", true);

        //MOCK
        doThrow(RuntimeException.class).when(mongoTemplate).updateMulti(expectedQuery, expectedUpdate, Message.class);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> messageServiceRepoImpl.getSingleConversation(userReceiver, usernameSender));
    }


}
