package com.hkprogrammer.api.domain.models;

import com.hkprogrammer.api.domain.models.enums.StatusChat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusChat status;

    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Schedule schedule;

    @Column(name = "data_criacao")
    private LocalDateTime dtCreated;

    @PrePersist
    public void prePersist() {
        if(id == null) {
            dtCreated = LocalDateTime.now();
            status = StatusChat.A;
        }
    }

}
