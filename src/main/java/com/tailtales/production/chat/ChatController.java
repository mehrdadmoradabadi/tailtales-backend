package com.tailtales.production.chat;

import com.tailtales.production.dto.ChatDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Operation(summary = "Fetch all chat's ", description = "It gives you all chats for the userId you sent.")
    @GetMapping("/{userId}")
    public List<ChatDto> findAllChats(@PathVariable Integer userId){
        return chatService.findAllChatsByUserId(userId);
    }

}
