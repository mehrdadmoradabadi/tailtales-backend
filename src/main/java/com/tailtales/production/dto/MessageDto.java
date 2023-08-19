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
    private ParticipantDto sender;
    private ParticipantDto receiver;

}
