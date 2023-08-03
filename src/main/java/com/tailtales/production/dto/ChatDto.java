package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDto {
    private String subject;
    private ParticipantDto firstParticipant;
    private ParticipantDto secondParticipant;
    private Integer chatId;

    public ChatDto(String subject, ParticipantDto firstParticipant, ParticipantDto secondParticipant, Integer chatId) {
        this.subject = subject;
        this.firstParticipant = firstParticipant;
        this.secondParticipant = secondParticipant;
        this.chatId = chatId;
    }
}
