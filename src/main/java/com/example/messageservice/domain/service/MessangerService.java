package com.example.messageservice.domain.service;

import com.example.messageservice.domain.model.User;
import com.example.messageservice.domain.model.messanger.GetMessageResponse;
import com.example.messageservice.domain.model.messanger.GetUsersResponse;
import com.example.messageservice.domain.model.messanger.PostMessageRequest;
import com.example.messageservice.domain.model.messanger.PostMessageResponse;

import java.util.List;

public interface MessangerService {

    public List<GetMessageResponse> getMessage(User userAuth, List<User> userList);

    //public List<GetUsersResponse> getUsers(User user);
    public List<GetUsersResponse> getUsers(List<User> userList, User userAuth);

    public PostMessageResponse sendMessage(PostMessageRequest request, User userAuth,  List<User> userList);
}
