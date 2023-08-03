package com.tailtales.production.message;

import com.tailtales.production.chat.Chat;
import com.tailtales.production.chat.ChatService;
import com.tailtales.production.dto.ChatDto;
import com.tailtales.production.dto.MessageDto;
import com.tailtales.production.dto.ParticipantDto;
import com.tailtales.production.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatService chatService;
    public Message mapFromDto(MessageRequestDTO message){
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setIsRead(message.getIsRead());
        newMessage.setSentDateTime(message.getSentDateTime());
        newMessage.setReceiverUserId(message.getReceiverUserId());
        newMessage.setSenderUserId(message.getSenderUserId());
        return newMessage;
    }
    public void sendMessage(MessageRequestDTO newMessage) {
        Message message = mapFromDto(newMessage);
        Chat chat = chatService.findOrCreateChatBetweenUsers(message.getSenderUserId(), message.getReceiverUserId());
        System.out.println(message);
        message.setChat(chat);
        messageRepository.save(message);
    }
    private ParticipantDto mapToChatParticipant(User user){
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setUsername(user.getUsername());
        participantDto.setId(user.getUserId());
        participantDto.setImage(user.getProfilePicture());
        return participantDto;
    }
    private MessageDto mapToMessageDto(Message message, ChatDto chatDto) {
        MessageDto messageDto = new MessageDto();
        messageDto.setChat(chatDto);
        messageDto.setMessageId(message.getMessageId());
        messageDto.setIsRead(message.getIsRead());
        messageDto.setSendDateTime(message.getSentDateTime());
        messageDto.setContent(message.getContent());
        return messageDto;
    }
    public List<MessageDto> getMessages(Integer chatId) {
        Chat chat = chatService.findById(chatId);
        ChatDto chatDto = new ChatDto(chat.getSubject(), mapToChatParticipant(chat.getFirstParticipant()), mapToChatParticipant(chat.getSecondParticipant()), chat.getChatId());

        List<Message> allMessage = messageRepository.findByChatId(chat);
        return allMessage.stream()
                .map(message -> mapToMessageDto(message, chatDto))
                .collect(Collectors.toList());
    }

}
