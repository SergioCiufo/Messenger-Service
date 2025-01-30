package com.example.messageservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //questo ci crea getter e setter in automatico
@Builder(toBuilder = true) //interfaccia differente e fluida per settare parametri //tobuilder da una classe di creare il builder
@AllArgsConstructor //costruttore con tutti i parametri
@NoArgsConstructor //costruttore vuoto
//@Entity
//@Table(name = "User")
public class User {
//    @Id
//    @GeneratedValue(Strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String username;

//    @OneToMany(mappedBy = "userSender", cascade = CascadeType.All)
    private List<Message> sentMessages;
//    @OneToMany(mappedBy = "userReceived", cascade = CascadeType.All)
    private List<Message> receivedMessages;
}
