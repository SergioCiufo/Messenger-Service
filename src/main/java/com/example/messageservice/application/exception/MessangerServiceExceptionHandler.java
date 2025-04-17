package com.example.messageservice.application.exception;


import com.example.messageservice.domain.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Log4j2
@Service
public class MessangerServiceExceptionHandler {
    private void logError(Exception ex, WebRequest request) {
        log.error(
                "An error happened while calling {} API: {}",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex.getMessage(), ex
        );
    }

    @ExceptionHandler({TokenUnauthorizedException.class})
    public ResponseEntity<Object> handleTokenUnauthorized(TokenUnauthorizedException ex, WebRequest request) {
        logError(ex, request);
        return ResponseEntity
                .status(401)
                .body(ex.getMessage());
    }

    @ExceptionHandler({EmptyFieldException.class})
    public ResponseEntity<Object> handleEmptyFieldException(EmptyFieldException ex, WebRequest request) {
        logError(ex, request);
        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        logError(ex, request);
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler({MessangerServiceException.class})
    public ResponseEntity<Object> handleMessangerServiceException(MessangerServiceException ex, WebRequest request) {
        logError(ex, request);
        return ResponseEntity.unprocessableEntity().build();
    }
}
