package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.api.FeignApi;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
import com.example.messageservice.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private FeignApi feignApi;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    public void shouldReturnUser_whenTokenIsValid() {
        //PARAMETERS
        String validToken = "valid.token.123";
        String authHeader = "Bearer " + validToken;
        User expectedUser = User.builder()
                .username("testUser")
                .build();

        //MOCK
        doReturn(Optional.of(expectedUser)).when(feignApi).verifyToken(validToken);

        //TEST
        Optional<User> result = tokenService.getUserByToken(authHeader);

        //RESULTS
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedUser, result.get());
        verify(feignApi, times(1)).verifyToken(validToken);
    }

    @Test
    public void shouldThrowException_whenTokenIsInvalid() {
        //PARAMETERS
        String invalidToken = "invalid.token";
        String authHeader = "Bearer " + invalidToken;

        //MOCK
        doThrow(TokenUnauthorizedException.class)
                .when(feignApi).verifyToken(invalidToken);

        //TEST + RESULTS
        Assertions.assertThrows(TokenUnauthorizedException.class,
                () -> tokenService.getUserByToken(authHeader));
        verify(feignApi, times(1)).verifyToken(invalidToken);
    }
}