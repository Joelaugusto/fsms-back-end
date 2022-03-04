package joel.fsms.modules.chat.service;

import joel.fsms.modules.chat.domain.Chat;
import joel.fsms.modules.chat.domain.ChatMapper;
import joel.fsms.modules.chat.domain.ChatRequest;
import joel.fsms.modules.chat.domain.ResumeChat;
import joel.fsms.modules.chat.persistence.ChatRepository;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public Chat findById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CHAT_NOT_FOUND"));
    }

    @Override
    public Chat changeName(Long id, String newName) {
        Chat chat = findById(id);
        chat.setName(newName);
        return chatRepository.save(chat);
    }

    @Override
    public Chat addMember(Long chatId, List<Long> memberId) {
        Chat chat = findById(chatId);
        Set<User> users = userRepository.findByIdIn(memberId);
        users.addAll(chat.getMembers());
        chat.setCreatedBy(loggedUser());
        chat.setMembers(users);
        return chatRepository.save(chat);
    }

    @Override
    @Transactional
    public Chat create(ChatRequest request) {
        Chat chat = ChatMapper.INSTANCE.toChat(request);
        Chat saved = chatRepository.save(chat);
        List<Long> members = request.getMembers();
        members.add(loggedUser().getId());
        addMember(chat.getId(), members);
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        chatRepository.deleteById(id);
    }

    @Override
    public List<Chat> fetchAll() {
        return chatRepository.findAll();
    }

    @Override
    public List<ResumeChat> findAllResumeChat() {
        return chatRepository.findAllResumeChat(loggedUser().getId()).stream().map(
                e-> new ResumeChat(
                        Long.parseLong(e.get("id").toString()),
                        e.get("name").toString(),
                        Long.parseLong( e.get("not_viewed").toString()))).collect(Collectors.toList());
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
