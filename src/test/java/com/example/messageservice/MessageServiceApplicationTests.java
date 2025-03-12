package com.example.messageservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void start(){
        Assertions.assertDoesNotThrow(()-> MessageServiceApplication.main(new String[] {}));
    }

}
