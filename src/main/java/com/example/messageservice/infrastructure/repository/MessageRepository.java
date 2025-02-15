package com.example.messageservice.infrastructure.repository;

import com.example.messageservice.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE LOWER(m.userSender) = LOWER(:userSender) OR LOWER(m.userReceiver) = LOWER(:userReceiver)")
    List<Message> findMessagesBySenderAndReceiverIgnoreCase(@Param("userSender") String userSender, @Param("userReceiver") String userReceiver);

}
