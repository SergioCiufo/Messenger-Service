package com.example.messageservice.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyFieldExceptionTest {
    @Test
    public void shouldThrowEmptyFieldException_withMessage() {
        //PARAMETERS
        String errorMessage = "errorMessage";

        //TEST
        EmptyFieldException exception = new EmptyFieldException(errorMessage);

        //RESULTS
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
}
