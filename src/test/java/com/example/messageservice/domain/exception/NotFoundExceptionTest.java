package com.example.messageservice.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {
    @Test
    public void shouldThrowNotFoundException_withMessage() {
        //PARAMETERS
        String errorMessage = "errorMessage";

        //TEST
        NotFoundException exception = new NotFoundException(errorMessage);

        //RESULTS
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
}
