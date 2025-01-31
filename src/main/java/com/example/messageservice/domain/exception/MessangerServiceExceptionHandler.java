package com.example.messageservice.domain.exception;

import com.example.messageservice.application.exception.MessangerServiceException;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Log4j2
public class MessangerServiceExceptionHandler {
    private void logError(Exception ex, WebRequest request) {
        log.error(
                "An error happened while calling {} API: {}",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex.getMessage(), ex
        );
    }

    @ExceptionHandler({MessangerServiceException.class})
    public ResponseEntity<Object> handleMessangerServiceException(MessangerServiceException ex, WebRequest request) {
        logError(ex, request);
        return ResponseEntity.unprocessableEntity().build();
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
}
