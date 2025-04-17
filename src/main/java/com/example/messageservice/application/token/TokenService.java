package com.example.messageservice.application.token;

import com.example.messageservice.domain.model.User;

import java.util.Optional;

public interface TokenService {
    Optional<User> getUserByToken (String authHeader);
}
