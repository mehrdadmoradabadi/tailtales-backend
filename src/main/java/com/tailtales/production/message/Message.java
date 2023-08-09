package com.tailtales.production.message;

import com.tailtales.production.chat.Chat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "message")
@Table(name = "message")
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private Chat chatId;

    @Column(name = "senderUserId", nullable = false)
    private Integer senderUserId;

    @Column(name = "receiverUserId", nullable = false)
    private Integer receiverUserId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "sentDateTime", nullable = false)
    private LocalDateTime sentDateTime;

    @Column(name = "isRead", nullable = false)
    private Boolean isRead;

}
