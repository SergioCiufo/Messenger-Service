package com.example.messageservice.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessangerServiceExceptionTest {
    @Test
    public void shouldThrowMessangerServiceException_withMessage() {
        //PARAMETERS
        String errorMessage = "errorMessage";

        //TEST
        MessangerServiceException exception = new MessangerServiceException(errorMessage);

        //RESULTS
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowMessangerServiceException_withMessageAndCause() {
        //PARAMETERS
        String errorMessage = "errorMessage";
        Throwable cause = new RuntimeException("errorCause");

        //TEST
        MessangerServiceException exception = new MessangerServiceException(errorMessage, cause);

        //RESULTS
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }
}
