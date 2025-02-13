package com.example.messageservice.domain.service.impl;

import com.example.messageservice.domain.api.UserServiceRepo;
import com.example.messageservice.domain.exception.NotFoundException;
import com.example.messageservice.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserServiceRepo userServiceRepo;

    public User getUserByUsername(String username){
        Optional<User> user = userServiceRepo.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        throw new NotFoundException("User not found"); //da rivedere
    }

    public List<User> getAllUsersExceptUser(User user) {
        return userServiceRepo.findAllUsersExcept(user);
    }
}
