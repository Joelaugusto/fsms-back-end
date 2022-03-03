package joel.fsms.modules.message.service;

import joel.fsms.modules.chat.domain.Chat;
import joel.fsms.modules.chat.persistence.ChatRepository;
import joel.fsms.modules.message.domain.Message;
import joel.fsms.modules.message.domain.MessageMapper;
import joel.fsms.modules.message.domain.MessageRequest;
import joel.fsms.modules.message.persistence.MessageRepository;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {


    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND")
        );
    }

    @Override
    public Message save(MessageRequest request) {

        Message message = MessageMapper.INSTANCE.toMessage(request);
        message.setChat(findChatById(request.getChatId()));
        message.setSentBy(loggedUser());
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        deleteById(id);
    }

    private Chat findChatById(Long id){
        return chatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CHAT NOT FOUND"));
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
