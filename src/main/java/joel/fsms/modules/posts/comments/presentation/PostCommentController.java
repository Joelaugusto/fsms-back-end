package joel.fsms.modules.posts.comments.presentation;

import joel.fsms.config.utils.PageJson;
import joel.fsms.modules.posts.comments.domain.PostCommentMapper;
import joel.fsms.modules.posts.comments.domain.PostCommentRequest;
import joel.fsms.modules.posts.comments.domain.PostCommentResponse;
import joel.fsms.modules.posts.comments.service.PostCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
@CrossOrigin
public class PostCommentController {

    private final PostCommentServiceImpl postCommentService;

    @GetMapping
    public ResponseEntity<PageJson<PostCommentResponse>> findAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable, @PathVariable Long postId) {
        return ResponseEntity.ok(PageJson.of(PostCommentMapper.MAPPER.toResponse(postCommentService.findAll(pageable, postId))));
    }

    @PostMapping
    public ResponseEntity<PostCommentResponse> save(@RequestBody PostCommentRequest request,@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PostCommentMapper.MAPPER.toResponse(postCommentService.save(request, postId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCommentResponse> findById(@PathVariable Long id, @PathVariable Long postId) {
        return ResponseEntity.ok(PostCommentMapper.MAPPER.toResponse(postCommentService.findById(id, postId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostCommentResponse> update(@RequestBody PostCommentRequest request,@PathVariable Long id,@PathVariable Long postId) {
        return ResponseEntity.ok(PostCommentMapper.MAPPER.toResponse(postCommentService.update(request, id, postId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,@PathVariable Long postId) {
        postCommentService.deleteById(id, postId);
        return ResponseEntity.noContent().build();
    }
}
