package com.example.messageservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //questo ci crea getter e setter in automatico
@Builder(toBuilder = true) //interfaccia differente e fluida per settare parametri //tobuilder da una classe di creare il builder
@AllArgsConstructor //costruttore con tutti i parametri
@NoArgsConstructor //costruttore vuoto
@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    private String userSender;
    private String userReceiver;

    private LocalDateTime createdAt;

    private Boolean isRead;
}
