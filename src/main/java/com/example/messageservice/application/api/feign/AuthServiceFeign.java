package com.example.messageservice.application.api.feign;

import com.example.messageservice.application.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@FeignClient(name = "auth-serviceFeign", url = "${spring.cloud.openfeign.client.rest.auth-service.url}") //host chiamata rest
public interface AuthServiceFeign {
    @PostMapping(value = "${spring.cloud.openfeign.client.rest.auth-service.api.verify-token}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
//    Object verifyToken(@RequestHeader("Authorization") String token); by nicola
    UserDto verifyToken(@RequestHeader("Authorization") String token);

    //ci prendiamo la lista utenti
    @GetMapping(value = "${spring.cloud.openfeign.client.rest.auth-service.api.username-list}",
            produces = MediaType.APPLICATION_JSON_VALUE) //non ha bisogno di consumes perché non ha un corpo da inviare
    List<UserDto> getUsers();
}
