package com.example.messageservice.application.service;

import com.example.messageservice.application.token.TokenService;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
import com.example.messageservice.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TokenFilter tokenFilter;

    @Test
    public void shouldReturnUnauthorized_whenAuthHeaderIsMissing() throws Exception {
        //PARAMETERS
        doReturn(null).when(request).getHeader("Authorization");

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verifyNoInteractions(tokenService);
        verifyNoInteractions(filterChain);
    }

    @Test
    public void shouldReturnUnauthorized_whenAuthHeaderIsInvalid() throws Exception {
        //PARAMETERS
        String invalidHeader = "InvalidHeader";
        doReturn(invalidHeader).when(request).getHeader("Authorization");

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verifyNoInteractions(tokenService);
        verifyNoInteractions(filterChain);
    }

    @Test
    public void shouldReturnUnauthorized_whenUserIsEmpty() throws Exception {
        //PARAMETERS
        String validHeader = "Bearer valid.token";
        doReturn(validHeader).when(request).getHeader("Authorization");
        doReturn(Optional.empty()).when(tokenService).getUserByToken(validHeader);

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(tokenService).getUserByToken(validHeader);
        verifyNoInteractions(filterChain);
    }

    @Test
    public void shouldAuthenticateAndContinue_whenTokenIsValid() throws Exception {
        //PARAMETERS
        String validHeader = "Bearer valid.token";
        User user = User.builder()
                .username("testUser").build();
        doReturn(validHeader).when(request).getHeader("Authorization");
        doReturn(Optional.of(user)).when(tokenService).getUserByToken(validHeader);

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(tokenService).getUserByToken(validHeader);
        verify(filterChain).doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertNotNull(authentication);
        Assertions.assertEquals(user, authentication.getPrincipal());
    }

    @Test
    public void shouldReturnUnauthorized_whenTokenServiceThrowsException() throws Exception {
        //PARAMETERS
        String validHeader = "Bearer invalid.token";
        doReturn(validHeader).when(request).getHeader("Authorization");
        doThrow(TokenUnauthorizedException.class).when(tokenService).getUserByToken(validHeader);

        //TEST
        tokenFilter.doFilterInternal(request, response, filterChain);

        //RESULTS
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(tokenService).getUserByToken(validHeader);
        verifyNoInteractions(filterChain);
    }
}