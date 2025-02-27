package com.example.messageservice.application.service;

import com.example.messageservice.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest {
    @InjectMocks
    private TokenFilter tokenFilter;

    @Mock
    private AuthServiceFeignImpl authServiceFeignImpl;

    @Mock
    private FilterChain filterChain;

    @Mock
    private MockHttpServletRequest  request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
    }

    @Test
    void shouldAuthenticate_whenTokenIsValid() throws Exception {
        //PARAMETERS
        User user = new User("validUser");
        request.addHeader("Authorization", "Bearer validToken");

        //MOCK
        when(authServiceFeignImpl.verifyToken("validToken")).thenReturn(user);

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(authServiceFeignImpl).verifyToken("validToken");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticate_whenAuthHeaderIsNull() throws Exception {;
        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);

    }

    @Test
    void shouldReturnUnauthorized_whenAuthHeaderIsInvalid() throws Exception {
        //PARAMETERS
        request.addHeader("Authorization", "InvalidToken");

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void shouldReturnUnauthorized_whenUserIsNull() throws Exception {
        //PARAMETERS
        request.addHeader("Authorization", "Bearer validToken");

        //MOCK
        doReturn(null).when(authServiceFeignImpl).verifyToken("validToken");

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(authServiceFeignImpl).verifyToken("validToken");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }
}
