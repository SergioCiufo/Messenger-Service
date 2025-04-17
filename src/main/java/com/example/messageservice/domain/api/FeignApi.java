package com.example.messageservice.domain.api;

import com.example.messageservice.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface FeignApi {
    Optional<User> verifyToken(String token);
    List<User> getUsers();
}
