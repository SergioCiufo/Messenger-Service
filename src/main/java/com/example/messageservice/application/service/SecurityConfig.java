package com.example.messageservice.application.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Service
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private TokenFilter tokenFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .cors().and()
//                .csrf()
//                    .ignoringRequestMatchers("/message", "/conversation")
//                    .and();
//
//        return http.build();
//    }
//}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of("*"));
            config.setAllowedMethods(List.of("*"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            cors.configurationSource(source);
        });

        http
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll() // o .authenticated()
                .and()
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}