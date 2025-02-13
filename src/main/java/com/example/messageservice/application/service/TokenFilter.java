package com.example.messageservice.application.service;
/*
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
@Log4j2
public class TokenFilter extends OncePerRequestFilter {

    private final AuthServiceAdapter authServiceAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Token non valido");
            return;
        }

        String token = authHeader.substring(7);
        String username = authServiceAdapter.validateToken(token);

        if (username == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Utente non valido");
            return;
        }

        //incapsuliamo i dati all'interno di questa funzione di springSecurity (al momento è anche troppo dato che stiamo passando una stringa)
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, Collections.emptyList()
        );

        //conserviamo le informazioni dell'utente autenticato, così spring security sa che l'utente è autenticato e che può effetturare le operazioni
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //passiamo al prossimo filtro della catena
        filterChain.doFilter(request, response);
    }
}

 */