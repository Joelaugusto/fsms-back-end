package joel.fsms.modules.groups.presentation;

import joel.fsms.config.utils.PageJson;
import joel.fsms.modules.groups.domain.*;
import joel.fsms.modules.groups.service.GroupServiceImpl;
import joel.fsms.modules.posts.domain.PostMapper;
import joel.fsms.modules.posts.domain.PostQuery;
import joel.fsms.modules.posts.domain.PostRequest;
import joel.fsms.modules.posts.domain.PostResponse;
import joel.fsms.modules.posts.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@CrossOrigin
public class GroupController {

    private final GroupServiceImpl groupService;
    private final PostServiceImpl postService;


    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(GroupMapper.INSTANCE.toResponse(groupService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll(){
        return ResponseEntity.ok(GroupMapper.INSTANCE.toResponse(groupService.findAllMyGroups()));
    }

    @GetMapping("/top")
    public ResponseEntity<PageJson<GroupResponse>> findOther(@PageableDefault(size = 12) Pageable pageable){
        return ResponseEntity.ok(PageJson.of(GroupMapper.INSTANCE.toResponse(groupService.findOtherGroups(pageable))));
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody CreateGroupRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupMapper.INSTANCE.toResponse(groupService.create(request)));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(@PathVariable Long id){
        groupService.join(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> update(@RequestBody UpdateGroupRequest request, @PathVariable Long id){
        return ResponseEntity.ok(GroupMapper.INSTANCE.toResponse(groupService.update(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/posts")
    public ResponseEntity<PageJson<PostResponse>> findAll(PostQuery query, @PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC, size = 20) Pageable pageable, @PathVariable Long groupId){
        return ResponseEntity.ok(PageJson.of(PostMapper.INSTANCE.mapToResponse(postService.findByGroupId(query,groupId, pageable))));
    }

    @PostMapping("/{groupId}/posts")
    public ResponseEntity<PostResponse> save(@RequestBody PostRequest request, @PathVariable Long groupId){
        return ResponseEntity.ok(PostMapper.INSTANCE.mapToResponse(postService.save(request, groupId)));
    }



    }
