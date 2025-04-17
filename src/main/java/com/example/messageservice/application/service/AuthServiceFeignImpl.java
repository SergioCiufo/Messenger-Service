package com.example.messageservice.application.service;

import com.example.messageservice.application.api.feign.AuthServiceFeign;
import com.example.messageservice.domain.api.FeignApi;
import com.example.messageservice.domain.exception.TokenUnauthorizedException;
import com.example.messageservice.application.mapper.UserMapper;
import com.example.messageservice.application.model.UserDto;
import com.example.messageservice.domain.model.User;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//il feign deve usare un modello di application per User e convertirlo nello user di domain.
//Che succede se un giorno cambia il body del messaggio?
// Sarà il feign service a prendere il modello di domain e convertirlo in quello di application
//Done

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceFeignImpl implements FeignApi {
    private final AuthServiceFeign authServiceFeign;
    private UserMapper userMapper;

    public Optional<User> verifyToken(String token) {
        try {
            UserDto userDto = authServiceFeign.verifyToken("Bearer " + token);
            User user = userMapper.mapToDomain(userDto);
            return Optional.of(user);
        } catch (FeignException.Forbidden e) {
            log.error("Token scaduto: {}", e.getMessage());
            throw new TokenUnauthorizedException("Token scaduto");
        }
    }

    public List<User> getUsers() {
        return authServiceFeign.getUsers().stream()
                .map(userMapper::mapToDomain)
                .collect(Collectors.toList());
    }
}