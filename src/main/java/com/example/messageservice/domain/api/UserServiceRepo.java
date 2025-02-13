package com.example.messageservice.domain.api;

import com.example.messageservice.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceRepo {
    Optional<User> findByUsername(String username);
    List<User> findAllUsersExcept(User user);
}
