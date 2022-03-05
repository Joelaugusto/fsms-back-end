package joel.fsms.modules.message.service;

import joel.fsms.modules.message.domain.Message;
import joel.fsms.modules.message.domain.MessageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    Message findById(Long id);
    Message save(MessageRequest request, Long chatId);
    Page<Message> findAll(Long chatId, Pageable pageable);
    void deleteById(Long id);
    void seeMessage(Long chatId);
}
