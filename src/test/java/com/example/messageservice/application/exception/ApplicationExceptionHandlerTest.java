package com.example.messageservice.application.exception;

import com.example.messageservice.domain.exception.ApplicationException;
import com.example.messageservice.domain.exception.EmptyFieldException;
import com.example.messageservice.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationExceptionHandlerTest {
    @InjectMocks
    private ApplicationExceptionHandler applicationExceptionHandler;

    @Test
    void shouldHandleApplicationException_whenExceptionThrown() {
        //PARAMETERS
        ApplicationException exception = new ApplicationException("Unauthorized");
        ServletWebRequest servletWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        //MOCK
        doReturn(httpServletRequest).when(servletWebRequest).getRequest();
        doReturn("/test-endpoint").when(httpServletRequest).getRequestURI();

        //TEST
        ResponseEntity<Object> result = applicationExceptionHandler.handleApplicationException(exception, servletWebRequest);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        Assertions.assertEquals("Unauthorized", result.getBody());
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
        ResponseEntity<Object> result = applicationExceptionHandler.handleEmptyFieldException(exception, servletWebRequest);


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
        ResponseEntity<Object> result = applicationExceptionHandler.handleNotFoundException(exception, servletWebRequest);


        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Assertions.assertEquals("Not Found", result.getBody());
    }
}
