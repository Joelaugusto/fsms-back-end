package joel.fsms.modules.chat.service;

import joel.fsms.modules.chat.domain.Chat;
import joel.fsms.modules.chat.domain.ChatRequest;
import joel.fsms.modules.chat.domain.ResumeChat;

import java.util.List;

public interface ChatService {

    Chat findById(Long id);
    Chat changeName(Long id, String newName);
    Chat addMember(Long chatId, List<Long> memberId);
    Chat create(ChatRequest request);
    void deleteById(Long id);
    List<Chat> fetchAll();
    List<ResumeChat> findAllResumeChat();
}
