package joel.fsms.modules.chat.service;

import joel.fsms.modules.chat.domain.Chat;

public interface ChatService {

    Chat findByid(Long id);
}
