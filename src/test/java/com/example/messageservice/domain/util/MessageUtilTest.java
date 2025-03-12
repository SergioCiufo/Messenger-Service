package com.example.messageservice.domain.util;


import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageUtilTest {
    @InjectMocks
    private MessageUtil messageUtil;

    @Test
    public  void shouldReturnEmpty_whenAllParamsAreNotNull(){
        //PARAMETERS
        String recevier = "receiverTest";
        String sender = "senderTest";
        String content = "contentTest";

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULT
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnNotEmpty_whenSenderIsNull(){
        //PARAMETERS
        String recevier = "receiverTest";
        String sender = null;
        String content = "contentTest";

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertNull(sender);
        Assertions.assertEquals("Sender", result);

    }

    @Test
    public void shouldReturnNotEmpty_whenReceiverIsNull(){
        //PARAMETERS
        String recevier = null;
        String sender = "senderTest";
        String content = "contentTest";

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertNull(recevier);
        Assertions.assertEquals("Receiver", result);
    }

    @Test
    public void shouldReturnNotEmpty_whenContentIsNull(){
        //PARAMETERS
        String recevier = "receiverTest";
        String sender = "senderTest";
        String content = null;

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertNull(content);
        Assertions.assertEquals("Content", result);
    }

    @Test
    public void shouldReturnNotEmpty_whenSenderAndReceiverAreNull(){
        //PARAMETERS
        String recevier = null;
        String sender = null;
        String content = "contentTest";

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertNull(recevier);
        Assertions.assertNull(sender);
        Assertions.assertEquals("Sender, Receiver", result);
    }

    @Test
    public void shouldReturnNotEmpty_whenSenderReceiverAndContentAreNull(){
        //PARAMETERS
        String recevier = null;
        String sender = null;
        String content = null;

        //TEST
        String result = messageUtil.checkNull(recevier, sender, content);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertNull(recevier);
        Assertions.assertNull(sender);
        Assertions.assertNull(content);
        Assertions.assertEquals("Sender, Receiver, Content", result);
    }
}
