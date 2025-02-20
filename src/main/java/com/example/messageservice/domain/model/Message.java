package com.example.messageservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data //questo ci crea getter e setter in automatico
@Builder(toBuilder = true) //interfaccia differente e fluida per settare parametri //tobuilder da una classe di creare il builder
@AllArgsConstructor //costruttore con tutti i parametri
@NoArgsConstructor //costruttore vuoto
@Document(collection = "Messages") //mongo db
public class Message {
    @Id //mongo db
    private String id; //in mongo string anziché int
    private String content;

    private String userSender;
    private String userReceiver;

    private LocalDateTime createdAt;

    private Boolean isRead;
}
