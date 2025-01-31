package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.messanger.FirstStepGetMessageResponse;
import com.example.messageservice.domain.model.messanger.FirstStepGetUsersResponse;
import com.example.messageservice.domain.model.messanger.FirstStepSendMessageRequest;
import com.example.messageservice.domain.model.messanger.FirstStepSendMessageResponse;

import java.util.List;

public interface MessangerService {

    public List<FirstStepGetMessageResponse> getMessageFirstStep();

    public List<FirstStepGetUsersResponse> getUsersFirstStep();

    public FirstStepSendMessageResponse sendMessageFirstStep(FirstStepSendMessageRequest request);
}
