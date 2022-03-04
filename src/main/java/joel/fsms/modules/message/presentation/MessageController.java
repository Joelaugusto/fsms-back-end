package joel.fsms.modules.message.presentation;

import joel.fsms.config.utils.PageJson;
import joel.fsms.modules.message.domain.MessageMapper;
import joel.fsms.modules.message.domain.MessageRequest;
import joel.fsms.modules.message.domain.MessageResponse;
import joel.fsms.modules.message.service.MessageServiceImpl;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats/{chatId}/messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {


    private final MessageServiceImpl messageService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable Long id, @PathVariable Long chatId){
        return ResponseEntity.ok(MessageMapper.INSTANCE.toResponse(messageService.findById(id), loggedUser()));
    }

    @GetMapping
    public ResponseEntity<PageJson<MessageResponse>> fetchAll(Pageable pageable, @PathVariable Long chatId){
        return ResponseEntity.ok(PageJson.of(MessageMapper.INSTANCE.toResponse(messageService.findAll(chatId, pageable), loggedUser())));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request, @PathVariable Long chatId){
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageMapper.INSTANCE.toResponse(messageService.save(request, chatId), loggedUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @PathVariable Long chatId){
        messageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
