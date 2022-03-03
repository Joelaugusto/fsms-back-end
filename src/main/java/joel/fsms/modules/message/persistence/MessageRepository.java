package joel.fsms.modules.message.persistence;

import joel.fsms.modules.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
