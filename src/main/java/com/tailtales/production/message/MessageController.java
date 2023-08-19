package com.tailtales.production.message;

import com.tailtales.production.dto.MessageDto;
import com.tailtales.production.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Operation(summary = "Send new message ", description = "Use this method to send message. you need to send the MessageRequestDTO object")
    @PostMapping(path = "/sendMessage", consumes = {"application/json"})
    public ApiResponse<MessageDto> sendMessage( @RequestBody MessageRequestDTO message){
        return new ApiResponse<>(messageService.sendMessage(message));
    }

    @Operation(summary = "Fetch all messages for a chat", description = "Use this to fetch all messages for a chat.")
    @GetMapping(path = "/getMessages/{chatId}")
    public ApiResponse<List<MessageDto>> getMessages(@PathVariable Integer chatId){
        return new ApiResponse<>(messageService.getMessages(chatId));
    }
}
