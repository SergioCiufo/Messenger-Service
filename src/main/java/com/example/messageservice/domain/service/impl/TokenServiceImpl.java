package com.example.messageservice.domain.service.impl;

import com.example.messageservice.application.token.TokenService;
import com.example.messageservice.domain.api.FeignApi;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
import com.example.messageservice.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final FeignApi feignApi;

    public Optional<User> getUserByToken(String authHeader) {
        try {
            String token = authHeader.substring(7);
            return feignApi.verifyToken(token);
        }catch(TokenUnauthorizedException ex){
            throw new TokenUnauthorizedException("Token non autorizzato");
        }
    }
}
