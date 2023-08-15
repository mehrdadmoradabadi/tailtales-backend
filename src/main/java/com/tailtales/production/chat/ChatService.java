package com.tailtales.production.chat;

import com.tailtales.production.dto.ChatDto;
import com.tailtales.production.dto.ParticipantDto;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserService userService;
    private ParticipantDto mapToChatParticipant(User user){
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setUsername(user.getUsername());
        participantDto.setId(user.getUserId());
        participantDto.setImage(user.getProfilePicture());
        participantDto.setFullName(user.getFirstName()+" "+user.getLastName());
        return participantDto;
    }
    public Chat add(Chat chat){
        return chatRepository.save(chat);
    }
    public Chat findById(Integer chatId){
        return chatRepository.findById(chatId).orElse(null);
    }
    public List<ChatDto> findAllChatsByUserId(Integer userId){
        List<Chat> allChats = chatRepository.findAllChatsForUserId(userId);
        return allChats.stream()
                .map(chat -> new ChatDto(
                        chat.getSubject(),
                        mapToChatParticipant(chat.getFirstParticipant()),
                        mapToChatParticipant(chat.getSecondParticipant()),
                        chat.getChatId()
                ))
                .collect(Collectors.toList());
    }
public Chat findOrCreateChatBetweenUsers(Integer firstParticipantId, Integer secondParticipantId) {
        User firstUser = userService.findById(firstParticipantId);
        User secondUser = userService.findById(secondParticipantId);
        Chat existingChat = chatRepository.findChatByParticipantIds(firstParticipantId, secondParticipantId);
        if(existingChat ==null){
            Chat newChat = new Chat();
            newChat.setSubject("Salam");
            newChat.setFirstParticipant(firstUser);
            newChat.setSecondParticipant(secondUser);
            return chatRepository.save(newChat);
        }
        return existingChat;
}

}
