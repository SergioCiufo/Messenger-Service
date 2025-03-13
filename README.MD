# Messenger Service - Chat4Me

## Descrizione

Il **servizio di messaggistica di Chat4Me** è un **microservizio scritto in Java utilizzando Spring Boot** che gestisce la messaggistica tra utenti. Il servizio si occupa dell'invio, della ricezione e della gestione delle conversazioni.

## Architettura

L'architettura del progetto è esagonale, ideale per Spring Boot, separando i componenti e mantenendo la logica di business pura e indipendente dai dettagli esterni.

## Funzionamento

1. Verifica del token: Ogni richiesta viene validata chiamando l'endpoint di verifica del token dell'Authentication Service tramite Feign Client.
2. Recupero della lista utenti: Gli utenti disponibili non sono memorizzati nel database del Messenger Service, ma vengono ottenuti chiamando l'endpoint /username-list dell'Authentication Service.
3. Recupero delle conversazioni: Gli utenti possono ottenere la lista completa delle proprie conversazioni.
4. Invio di messaggi: Gli utenti possono inviare messaggi ad altri utenti, creando o aggiornando conversazioni esistenti.
5. Recupero di una singola conversazione: È possibile ottenere i dettagli di una conversazione specifica tra due utenti.

## Database

Il servizio utilizza Mongo Database con Mongo Repository per gestire i dati.

Per garantire la portabilità e la sicurezza, è stato configurato un Datasource centralizzato nel file `application.properties`, evitando di scrivere direttamente le credenziali nel codice e semplificando la gestione dei dati di connessione in ambienti diversi.

## Token

I token JWT vengono gestiti dal Servizio di Autenticazione. 
Ogni richiesta del Servizio di Messaggistica dev'essere validata dal Servizio di Autenticazione. Se il token è valido, restituisce l'informazione dell'utente loggato.

## Sicurezza

- **Spring Security**: Configurato per mettere in whitelist le chiamate di verifica del token e di recupero utenti per evitare problemi CORS.
- **Feign Client**: Usato per verificare il token e ottenere la lista utenti.

## Endpoints Principali

| Metodo | Endpoint      | Descrizione                                                             |
|--------|---------------|-------------------------------------------------------------------------|
| GET    | /messages     | Recupera la lista di tutte le conversazioni dell'utente.                                               |
| GET    | /users        | Recupera la lista di utenti (chiamata all'Authentication Service).                                        |
| POST   | /message      | Invia un messaggio a un utente.                       |
| POST   | /conversation | Recupera una singola conversazione di un utente.                                   |
