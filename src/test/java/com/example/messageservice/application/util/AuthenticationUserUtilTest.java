package com.example.messageservice.application.util;

import com.example.messageservice.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationUserUtilTest {
    @InjectMocks
    private AuthenticationUserUtil authenticationUserUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldGetUserAuth_whenAllOk(){
        //MOCK
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn(mockUser).when(authentication).getPrincipal();

        //TEST
        User result  = authenticationUserUtil.getUserAuth();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void shouldThrowException_whenNoAuthenticationIsPresent() {
        //MOCK
        doReturn(null).when(securityContext).getAuthentication();

        // TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> authenticationUserUtil.getUserAuth());
    }

    @Test
    void shouldThrowException_whenPrincipalIsNotUser() {
        //MOCK
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn("Not User").when(authentication).getPrincipal();

        //TEST + RESULTS
        Assertions.assertThrows(ClassCastException.class, () -> authenticationUserUtil.getUserAuth());
    }
}
