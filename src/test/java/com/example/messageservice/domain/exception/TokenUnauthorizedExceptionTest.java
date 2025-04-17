package com.example.messageservice.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenUnauthorizedExceptionTest {
    @Test
    public void shouldThrowTokenUnauthorizedException_withMessage() {
        //PARAMETERS
        String errorMessage = "errorMessage";

        //TEST
        TokenUnauthorizedException exception = new TokenUnauthorizedException(errorMessage);

        //RESULTS
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
}
