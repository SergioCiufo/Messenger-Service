package com.example.messageservice.application.service;

import com.example.messageservice.application.api.feign.AuthServiceFeign;
import com.example.messageservice.application.mapper.UserMapper;
import com.example.messageservice.application.model.UserDto;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
import com.example.messageservice.domain.model.User;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceFeignImpl authServiceFeignImpl;

    @Mock
    private AuthServiceFeign authServiceFeign;

    @Mock
    private UserMapper userMapper;

    @Test
    public void shouldReturnUser_whenTokenIsValid() {
        //PARAMETERS
        String token = "valid.token";
        String bearerToken = "Bearer " + token;
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");

        User expectedUser = User.builder()
                .username("testUser")
                .build();

        //MOCK
        doReturn(userDto).when(authServiceFeign).verifyToken(bearerToken);
        doReturn(expectedUser).when(userMapper).mapToDomain(userDto);

        //TEST
        Optional<User> result = authServiceFeignImpl.verifyToken(token);

        //RESULTS
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedUser, result.get());
        verify(authServiceFeign).verifyToken(bearerToken);
        verify(userMapper).mapToDomain(userDto);

    }

    @Test
    public void shouldThrowException_whenTokenIsForbidden() {
        //PARAMETERS
        String token = "expired.token";
        String bearerToken = "Bearer " + token;

        //MOCK
        doThrow(FeignException.Forbidden.class)
                .when(authServiceFeign).verifyToken(bearerToken);

        //TEST + RESULTS
        Assertions.assertThrows(TokenUnauthorizedException.class,
                () -> authServiceFeignImpl.verifyToken(token));
        verify(authServiceFeign).verifyToken(bearerToken);
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldThrowException_whenVerifyTokenIsInvalid() {
        //PARAMETERS
        String token = "invalid";

        //MOCK
        doThrow(RuntimeException.class).when(authServiceFeign).verifyToken("Bearer " + token);

        //TEST + RESULTS
        Assertions.assertThrows(RuntimeException.class, () -> authServiceFeignImpl.verifyToken(token));
        verify(authServiceFeign, times(1)).verifyToken("Bearer " + token);
    }

    @Test
    void shouldGetUsersList_whenAllOk() {
        //PARAMETERS
        UserDto userDto1 = new UserDto("userTest");
        UserDto userDto2 = new UserDto("userTest2");
        User user1 = new User("userTest1");
        User user2 = new User("userTest2");

        List<UserDto> userDtoList = List.of(userDto1, userDto2);
        List<User> expectedUserList = List.of(user1, user2);

        //MOCK
        when(authServiceFeign.getUsers()).thenReturn(userDtoList);
        when(userMapper.mapToDomain(userDto1)).thenReturn(user1);
        when(userMapper.mapToDomain(userDto2)).thenReturn(user2);

        //TEST
        List<User> result = authServiceFeignImpl.getUsers();

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUserList.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedUserList));

        verify(authServiceFeign, times(1)).getUsers();
        verify(userMapper, times(1)).mapToDomain(userDto1);
        verify(userMapper, times(1)).mapToDomain(userDto2);
    }

    @Test
    void shouldThrowException_whenGetUsersFails() {
        //MOCK
        when(authServiceFeign.getUsers()).thenThrow(RuntimeException.class);

        //TEST
        Assertions.assertThrows(RuntimeException.class, () -> {
            authServiceFeignImpl.getUsers();
        });

        //RESULTS
        verify(authServiceFeign, times(1)).getUsers();
    }
}
