package com.example.messageservice.application.mapper;

import com.example.messageservice.application.model.UserDto;
import com.example.messageservice.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToDomain(UserDto userDto) {
        if(userDto == null) return null;
        return new User(userDto.getUsername());
    }
}
