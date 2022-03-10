package joel.fsms.modules.chat.presention;

import joel.fsms.modules.chat.domain.*;
import joel.fsms.modules.chat.service.ChatServiceImpl;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

    private final ChatServiceImpl chatService;

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(ChatMapper.INSTANCE.toResponse(chatService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> findById(){
        return ResponseEntity.ok(ChatMapper.INSTANCE.toResponse(chatService.fetchAll()));
    }

    @GetMapping("/resume")
    public ResponseEntity<List<ResumeChat>> fetchAllResumeChat(){
        return ResponseEntity.ok(chatService.findAllResumeChat());
    }

    @Transactional
    @GetMapping("/{id}/with-messages")
    public ResponseEntity<ChatWithMessages> fetchWithMessagesChat(@PathVariable Long id){
        return ResponseEntity.ok(ChatMapper.INSTANCE.toChatWithMessages(chatService.findById(id), loggedUser().getId()));
    }

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestBody ChatRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMapper.INSTANCE.toResponse(chatService.create(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        chatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
