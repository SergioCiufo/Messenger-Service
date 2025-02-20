package com.example.messageservice.infrastructure.repository;

import com.example.messageservice.domain.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Integer> {

    //@Query("SELECT m FROM Message m WHERE LOWER(m.userSender) = LOWER(:userSender) OR LOWER(m.userReceiver) = LOWER(:userReceiver)") IN JPA
    @Query("{'$or': [{'userSender': ?0}, {'userReceiver': ?1}]}") //IN MONGO
    //List<Message> findMessagesBySenderAndReceiver(@Param("userSender") String userSender, @Param("userReceiver") String userReceiver);
    List<Message> findMessagesBySenderAndReceiver(String userSender, String userReceiver);

//    @Query("SELECT m FROM Message m WHERE (LOWER(m.userSender) = LOWER(:userA) AND LOWER(m.userReceiver) = LOWER(:userB)) " +
//            "OR (LOWER(m.userReceiver) = LOWER(:userA) AND LOWER(m.userSender) = LOWER(:userB)) " +
//            "ORDER BY m.createdAt ASC") //IN JPA
    @Query("{'$or': [ {'$and': [{'userSender': ?0}, {'userReceiver': ?1}]}, {'$and': [{'userReceiver': ?0}, {'userSender': ?1}]} ]}") //IN MONGO
    //List<Message> findSingleConversation(@Param("userA") String userA, @Param("userB") String userB);
    List<Message> findSingleConversation(String userA, String userB);

    //IN MONGO NON SI PUò FARE L'UPDATE COME IN JPA, QUINDI UTILIZZIAMO LE CRITERIA QUERY E MONGO TEMPLATE PER AGGIORNARE
//    @Modifying
//    @Query("UPDATE Message m SET m.isRead = true WHERE LOWER(m.userSender) = LOWER(:userSender) AND LOWER(m.userReceiver) = LOWER(:userReceiver)") //IN JPA
//    void updateRead(@Param("userSender") String userSender, @Param("userReceiver") String userReceiver);

}
