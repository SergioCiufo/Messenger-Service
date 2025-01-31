package com.example.messageservice.domain.utill;

import com.example.messageservice.domain.exception.NotFoundException;
import com.example.messageservice.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserListUtil {
    private List<User> userList = new ArrayList<>(){{
        add(new User(1,"admin", "admin", new ArrayList<>(), new ArrayList<>()));
        add(new User(2,"name", "username", new ArrayList<>(), new ArrayList<>()));
        add(new User(3,"test", "test", new ArrayList<>(), new ArrayList<>()));
    }};

    public List<User> getUserList() {
        return userList;
    }

    public User getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Utente non trovato: " + username));
    }
}
