package com.example.messageservice.infrastructure.repository;

import com.example.messageservice.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE LOWER(m.userSender) = LOWER(:userSender) OR LOWER(m.userReceiver) = LOWER(:userReceiver)")
    List<Message> findMessagesBySenderAndReceiver(@Param("userSender") String userSender, @Param("userReceiver") String userReceiver);

    @Query("SELECT m FROM Message m WHERE (LOWER(m.userSender) = LOWER(:userA) AND LOWER(m.userReceiver) = LOWER(:userB)) " +
            "OR (LOWER(m.userReceiver) = LOWER(:userA) AND LOWER(m.userSender) = LOWER(:userB)) " +
            "ORDER BY m.createdAt ASC")
    List<Message> findSingleConversation(@Param("userA") String userA, @Param("userB") String userB);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE LOWER(m.userSender) = LOWER(:userSender) AND LOWER(m.userReceiver) = LOWER(:userReceiver)")
    void updateRead(@Param("userSender") String userSender, @Param("userReceiver") String userReceiver);

}
