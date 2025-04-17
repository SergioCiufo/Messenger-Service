package com.example.messageservice.application.exception;

import com.example.messageservice.domain.exception.EmptyFieldException;
import com.example.messageservice.domain.exception.MessangerServiceException;
import com.example.messageservice.domain.exception.NotFoundException;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
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

    @Test
    void shouldHandleTokenUnauthorizedException_whenExceptionThrown() {
        //PARAMETERS
        TokenUnauthorizedException exception = new TokenUnauthorizedException("unathorized");
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test-endpoint").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = messangerServiceExceptionHandler.handleTokenUnauthorized(exception, servletWebRequest);


        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        Assertions.assertEquals("unathorized", result.getBody());
    }

        @Test
        void shouldHandleEmptyFieldException_whenExceptionThrown() {
        //PARAMETERS
        EmptyFieldException exception = new EmptyFieldException("Bad Request");
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test-endpoint").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = messangerServiceExceptionHandler.handleEmptyFieldException(exception, servletWebRequest);


        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertEquals("Bad Request", result.getBody());
    }

    @Test
    void shouldHandleNotFoundException_whenExceptionThrown() {
        //PARAMETERS
        NotFoundException exception = new NotFoundException("Not Found");
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test-endpoint").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = messangerServiceExceptionHandler.handleNotFoundException(exception, servletWebRequest);


        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Assertions.assertEquals("Not Found", result.getBody());
    }

    @Test
    void shouldHandleMessangerServiceException_whenExceptionThrown() {
        //PARAMETERS
        MessangerServiceException exception = new MessangerServiceException("error");
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = messangerServiceExceptionHandler.handleMessangerServiceException(exception, servletWebRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        Assertions.assertNull(result.getBody());
    }
}
