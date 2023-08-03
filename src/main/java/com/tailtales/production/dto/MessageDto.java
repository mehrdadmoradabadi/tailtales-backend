package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDto {
    private String content;
    private Long messageId;
    private ChatDto chat;
    private LocalDateTime sendDateTime;
    private Boolean isRead;

    public MessageDto(String content, Long messageId, ChatDto chat, LocalDateTime sendDateTime, Boolean isRead) {
        this.content = content;
        this.messageId = messageId;
        this.chat = chat;
        this.sendDateTime = sendDateTime;
        this.isRead = isRead;
    }
}
