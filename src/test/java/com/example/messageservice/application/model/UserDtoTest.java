package com.example.messageservice.application.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    void shouldCreateUserDto_whenAllOk(){
        //TEST
        UserDto userDto = UserDto.builder()
                .username("testUsername")
                .build();

        //RESULTS
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getUsername(), "testUsername");
    }
}
