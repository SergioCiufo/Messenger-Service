package com.example.messageservice.application.service;

import com.example.messageservice.application.api.feign.AuthServiceFeign;
import com.example.messageservice.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceFeignImpl {
    private final AuthServiceFeign authServiceFeign;

    public User verifyToken(String token) {
        User user = authServiceFeign.verifyToken("Bearer " + token);
        return user;
    }

    public List<User> getUsers() {
        List<Object> users = authServiceFeign.getUsers();
        log.info(users);
        // Effettua il cast della lista
        List<User> userList = (List<User>) (List<?>) users; // Cast generico per evitare il tipo Object
        //return authServiceFeign.getUsers();
        return userList;
    }
}
