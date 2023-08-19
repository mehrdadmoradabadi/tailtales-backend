package com.tailtales.production.message;

import com.tailtales.production.chat.Chat;
import com.tailtales.production.chat.ChatService;
import com.tailtales.production.dto.ChatDto;
import com.tailtales.production.dto.MessageDto;
import com.tailtales.production.dto.ParticipantDto;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
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
    public MessageDto sendMessage(MessageRequestDTO newMessage) {
        Message message = mapFromDto(newMessage);
        Chat chat = chatService.findOrCreateChatBetweenUsers(message.getSenderUserId(), message.getReceiverUserId());
        message.setChatId(chat);
        message = messageRepository.save(message);
        ChatDto chatDto = new ChatDto(chat.getSubject(), mapToChatParticipant(chat.getFirstParticipant()), mapToChatParticipant(chat.getSecondParticipant()), chat.getChatId());
        return mapToMessageDto(message,chatDto);
    }
    private ParticipantDto mapToChatParticipant(User user){
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setUsername(user.getUsername());
        participantDto.setId(user.getUserId());
        participantDto.setImage(user.getProfilePicture());
        participantDto.setFullName(user.getFirstName()+" "+user.getLastName());
        return participantDto;
    }
    private MessageDto mapToMessageDto(Message message, ChatDto chatDto) {
        User sender = userRepository.findById(message.getSenderUserId()).orElse(null);
        MessageDto messageDto = new MessageDto();
        messageDto.setChat(chatDto);
        messageDto.setMessageId(message.getMessageId());
        messageDto.setIsRead(message.getIsRead());
        messageDto.setSendDateTime(message.getSentDateTime());
        messageDto.setContent(message.getContent());
        assert sender != null;
        messageDto.setSender(chatDto.getFirstParticipant().getId().equals(message.getSenderUserId())? chatDto.getFirstParticipant():chatDto.getSecondParticipant());
        messageDto.setReceiver(chatDto.getSecondParticipant().getId().equals(message.getReceiverUserId())? chatDto.getSecondParticipant():chatDto.getFirstParticipant());
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
