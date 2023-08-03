package com.tailtales.production.message;

import com.tailtales.production.chat.Chat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageRequestDTO {
    private Integer senderUserId;
    private Integer receiverUserId;
    private String content;
    private LocalDateTime sentDateTime;
    private Boolean isRead;
}
