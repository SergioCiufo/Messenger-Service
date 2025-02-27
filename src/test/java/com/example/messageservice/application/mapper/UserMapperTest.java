package com.example.messageservice.application.mapper;

import com.example.messageservice.application.model.UserDto;
import com.example.messageservice.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private UserMapper userMapper;

    @Test
    void shouldMapToDomain_whenUserDtoIsValid() {
        //PARAMETRI
        UserDto userDto = new UserDto("userTest");

        //TEST
        User result = userMapper.mapToDomain(userDto);

        //RESULTS
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    void shouldReturnNull_whenUserDtoIsNull() {
        //TEST
        User result = userMapper.mapToDomain(null);

        //RESULTS
        Assertions.assertNull(result);
    }
}
