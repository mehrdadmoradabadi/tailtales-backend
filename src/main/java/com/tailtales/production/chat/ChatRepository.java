package com.tailtales.production.chat;

import com.tailtales.production.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
    @Query("SELECT c FROM chats c WHERE " +
            "(c.firstParticipant.userId = :firstParticipantId AND c.secondParticipant.userId = :secondParticipantId) " +
            "OR (c.firstParticipant.userId = :secondParticipantId AND c.secondParticipant.userId = :firstParticipantId)")
    Chat findChatByParticipantIds(@Param("firstParticipantId") Integer firstParticipantId,
                                  @Param("secondParticipantId") Integer secondParticipantId);

    @Query("SELECT c FROM chats c WHERE c.firstParticipant.userId = :userId OR c.secondParticipant.userId = :userId")
    List<Chat> findAllChatsForUserId(@Param("userId") Integer userId);
}
