package joel.fsms.modules.groups.presentation;

import joel.fsms.modules.groups.domain.*;
import joel.fsms.modules.groups.service.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupServiceImpl groupService;


    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(GroupMapper.INSTANCE.toResponse(groupService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll(){
        return ResponseEntity.ok(GroupMapper.INSTANCE.toResponse(groupService.findAll()));
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



    }
