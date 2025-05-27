package com.example.messageservice.application.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerKafka {

    @Bean
    public Consumer<String> messageConsumer() {
        return message -> {
            System.out.println("Messaggio consumato: " + message);
        };
    }

}
