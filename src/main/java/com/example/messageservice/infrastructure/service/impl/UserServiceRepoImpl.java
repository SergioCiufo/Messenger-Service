package com.example.messageservice.infrastructure.service.impl;

import com.example.messageservice.domain.api.UserServiceRepo;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceRepoImpl implements UserServiceRepo {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public List<User> findAllUsersExcept(User user) {
        return userRepository.findByUsernameNotIgnoreCase(user.getUsername());
    }
}
