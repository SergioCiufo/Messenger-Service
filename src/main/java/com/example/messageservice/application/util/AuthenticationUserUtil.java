package com.example.messageservice.application.util;

import com.example.messageservice.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUserUtil {
    public User getUserAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        User userAuth = (User) authentication.getPrincipal();
        return userAuth;
    }
}
