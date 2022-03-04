package joel.fsms.modules.chat.presention;

import joel.fsms.modules.chat.domain.ChatMapper;
import joel.fsms.modules.chat.domain.ChatRequest;
import joel.fsms.modules.chat.domain.ChatResponse;
import joel.fsms.modules.chat.domain.ResumeChat;
import joel.fsms.modules.chat.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
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

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestBody ChatRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMapper.INSTANCE.toResponse(chatService.create(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        chatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
