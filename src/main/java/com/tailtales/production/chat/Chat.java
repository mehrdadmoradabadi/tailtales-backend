package com.tailtales.production.chat;

import com.tailtales.production.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "chats")
@Table(name = "chats")
@Data
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatId")
    private Integer chatId;

    @Column(name = "subject")
    private String subject;


    @ManyToOne
    @JoinColumn(name = "firstParticipant",referencedColumnName = "userId")
    private User firstParticipant;

    @ManyToOne
    @JoinColumn(name = "secondParticipant",referencedColumnName = "userId")
    private User secondParticipant;

    public Chat(Integer chatId, String subject, User firstParticipantId, User secondParticipantId) {
        this.chatId = chatId;
        this.subject = subject;
        this.firstParticipant = firstParticipantId;
        this.secondParticipant = secondParticipantId;
    }
}
