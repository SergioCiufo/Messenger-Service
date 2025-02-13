package com.example.messageservice.application.service;
/*
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceAdapter {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String AUTH_SERVICE_URL = "http://localhost:8080/verify-token";

    public String validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token); //Impostiamo l'intestazione Authorization: Berer <token>

            //Creiamo la richiesta con le intestazioni
            HttpEntity<String> entity = new HttpEntity<>(headers);

            //mandiamo una richiesta http in post servizio di autenticazione, con l'endpoint verify token in POST per inviare dati (il token nell'entity) e la stringa come tipo di dato che vogliamo in risposta
            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) { //se va tutto bene l'access token è verificato e ci prendiamo l'oggetto che porta
                return response.getBody();
            } else { //impostare che se va male inviamo la richiesta per il refreshare il token
                log.error("Errore nella validazione del token: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("Errore durante la validazione del token", e);
            return null;
        }
    }
}

 */