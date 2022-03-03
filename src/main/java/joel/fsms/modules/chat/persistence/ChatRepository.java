package joel.fsms.modules.chat.persistence;

import joel.fsms.modules.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
