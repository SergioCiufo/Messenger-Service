package com.example.messageservice.domain.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FirstStepGetMessageResponse {
    private String id;
    private String content;
    private String username_sender;
    private String username_receiver;
}
