package com.example.messageservice.application.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors().and()
                .csrf()
                    .ignoringRequestMatchers("/message", "/conversation")
                    .and(); //così le chiamate in post funzionano, ma è errato poiché noi lavoriamo con un cookie. bisogna settarlo meglio.

        return http.build();
    }
}