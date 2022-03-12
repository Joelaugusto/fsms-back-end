package joel.fsms.modules.posts.presentation;


import joel.fsms.config.utils.PageJson;
import joel.fsms.modules.posts.domain.PostMapper;
import joel.fsms.modules.posts.domain.PostRequest;
import joel.fsms.modules.posts.domain.PostResponse;
import joel.fsms.modules.posts.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping
    public ResponseEntity<PageJson<PostResponse>> findAll(String query,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable){
        return ResponseEntity.ok(PageJson.of(PostMapper.INSTANCE.mapToResponse(postService.findAll(query, pageable))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(PostMapper.INSTANCE.mapToResponse(postService.findByIdAndIncrementVisualizations(id)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> save(@RequestBody PostRequest request){
        return ResponseEntity.ok(PostMapper.INSTANCE.mapToResponse(postService.save(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@RequestBody PostRequest request,@PathVariable Long id){
        return ResponseEntity.ok(PostMapper.INSTANCE.mapToResponse(postService.update(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
