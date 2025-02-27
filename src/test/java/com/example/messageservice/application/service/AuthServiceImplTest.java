package com.example.messageservice.application.service;

import com.example.messageservice.application.api.feign.AuthServiceFeign;
import com.example.messageservice.application.mapper.UserMapper;
import com.example.messageservice.application.model.UserDto;
import com.example.messageservice.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
    void shouldVerifyTokenReturnUser_whenAllOk() {
        //PARAMETERS
        String token = "token";
        UserDto userDto = new UserDto("userTest");
        User expectedUser = new User("userTest");

        //MOCK
        doReturn(userDto).when(authServiceFeign).verifyToken("Bearer " + token);
        when(userMapper.mapToDomain(userDto)).thenReturn(expectedUser);

        //TEST
        User result = authServiceFeignImpl.verifyToken(token);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUser.getUsername(), result.getUsername());
        verify(authServiceFeign, times(1)).verifyToken("Bearer " + token);
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
