package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.utill.UserListUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserListUtil userListUtil;

    public List<User> getUsersList() {
        return userListUtil.getUserList();
    }

    public User getUserByUsername(String username) {
        return userListUtil.getUserByUsername(username);
    }
}
