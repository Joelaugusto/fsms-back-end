package joel.fsms.modules.chat.persistence;

import joel.fsms.modules.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    @Query(value = "select c.id, c.name, (select count(m.id) from messages m where m.chat_id = c.id and m.seen_at is null) as not_viewed from chats c inner join chats_users cu on c.id = cu.chat_id inner join users u on cu.user_id = u.id and u.id = ?1  order by c.updated_at desc", nativeQuery = true)
    List<Map> findAllResumeChat(Long userId);

}
