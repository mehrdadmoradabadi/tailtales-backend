package com.tailtales.production.chat;

import com.tailtales.production.dto.ChatDto;
import com.tailtales.production.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Operation(summary = "Fetch all chat's ", description = "It gives you all chats for the userId you sent.")
    @GetMapping("/{userId}")
    public ApiResponse<List<ChatDto>> findAllChats(@PathVariable Integer userId){
        return new ApiResponse<>(chatService.findAllChatsByUserId(userId)) ;
    }

}
