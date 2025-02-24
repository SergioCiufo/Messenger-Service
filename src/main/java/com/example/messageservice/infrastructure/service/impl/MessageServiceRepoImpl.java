package com.example.messageservice.infrastructure.service.impl;

import com.example.messageservice.domain.repository.MessageServiceRepo;
import com.example.messageservice.domain.model.Message;
import com.example.messageservice.domain.model.User;
import com.example.messageservice.infrastructure.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceRepoImpl  implements MessageServiceRepo {
    private final MessageRepository messageRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Message> getAllMessages(User user) {
        return messageRepository.findMessagesBySenderAndReceiver(user.getUsername(), user.getUsername());
    }

    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    //MONGOTEMPLATE è LA CLASSE PRINCIPALE DI SPRING DATA MONGODB, CHE PERMETTE DI INTERAGIRE DIRETTAMENTE CON MONGODB SNEZA PASSARE PER MONGOREPOSITORY
    //E' MOLTO POTENTE E CONSENTE DI ESEGUIRE QUERY PERSONALIZZATE, AGGIORNAMENTI, ELIMINAZIONE ETC
    //PER USARLO SI USANO LE CRITERIA QUERY
    @Override
    @Transactional
    public List<Message> getSingleConversation(User user, String usernameConversation){
        Query query = new Query(Criteria.where("userSender").is(usernameConversation) //CRITERIA QUERY
                .and("userReceiver").is(user.getUsername())); //CRITERIA QUERY
        Update update = new Update().set("isRead", true); //CRITERIA QUERY
        mongoTemplate.updateMulti(query, update, Message.class); //MONGO TEMPLATE
        return messageRepository.findSingleConversation(user.getUsername(), usernameConversation);
    }

}
