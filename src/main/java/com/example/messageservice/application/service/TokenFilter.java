package com.example.messageservice.application.service;

import com.example.messageservice.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@AllArgsConstructor
@Log4j2
public class TokenFilter extends OncePerRequestFilter {

    private final AuthServiceFeignImpl authServiceFeignImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Token non valido");
            return;
        }

        String token = authHeader.substring(7);
        User user = authServiceFeignImpl.verifyToken(token);

        if (user.getUsername() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Utente non valido");
            return;
        }
        log.info("Token valido");
        //incapsuliamo i dati all'interno di questa funzione di springSecurity (al momento è anche troppo dato che stiamo passando una stringa)
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                username, null, Collections.emptyList()
//        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                //utente //credenziali //ruolo
                user, null, null);

        //conserviamo le informazioni dell'utente autenticato, così spring security sa che l'utente è autenticato e che può effetturare le operazioni
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //passiamo al prossimo filtro della catena
        filterChain.doFilter(request, response);
    }
}