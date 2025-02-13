package com.example.messageservice.infrastructure.repository;

import com.example.messageservice.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> getMessagesByUserSender_IdOrUserReceiver_Id(int userSenderId, int userReceiverId);
}
