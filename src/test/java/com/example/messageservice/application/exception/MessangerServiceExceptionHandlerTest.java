package com.example.messageservice.application.exception;

import com.example.messageservice.domain.exception.MessangerServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MessangerServiceExceptionHandlerTest {
    @InjectMocks
    private MessangerServiceExceptionHandler messangerServiceExceptionHandler;

    @Test
    void shouldHandleMessangerServiceException_whenExceptionIsThrown() {
        //PARAMETERS
        MessangerServiceException exception = mock(MessangerServiceException.class);
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test-endpoint").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = messangerServiceExceptionHandler.handleMessangerServiceException(exception, servletWebRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        Assertions.assertNull(result.getBody());
    }
}
