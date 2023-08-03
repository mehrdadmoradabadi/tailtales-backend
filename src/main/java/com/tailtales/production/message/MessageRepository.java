package com.tailtales.production.message;

import com.tailtales.production.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findByChatId(Chat chatId);
}
