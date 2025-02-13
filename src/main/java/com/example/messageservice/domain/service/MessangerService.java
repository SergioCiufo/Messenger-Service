package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.messanger.GetMessageResponse;
import com.example.messageservice.domain.model.messanger.GetUsersResponse;
import com.example.messageservice.domain.model.messanger.SendMessageRequest;
import com.example.messageservice.domain.model.messanger.SendMessageResponse;

import java.util.List;

public interface MessangerService {

    public List<GetMessageResponse> getMessage();

    public List<GetUsersResponse> getUsers();

    public SendMessageResponse sendMessage(SendMessageRequest request);
}
