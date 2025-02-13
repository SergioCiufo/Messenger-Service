package com.example.messageservice.application.api.feign;

import com.example.messageservice.domain.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "auth-serviceFeign", url = "${spring.cloud.openfeign.client.rest.auth-service.url}") //host chiamata rest
public interface AuthServiceFeign {
    @PostMapping(value = "${spring.cloud.openfeign.client.rest.auth-service.api.verify-token}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
//    Object verifyToken(@RequestHeader("Authorization") String token); by nicola
    User verifyToken(@RequestHeader("Authorization") String token);
}
