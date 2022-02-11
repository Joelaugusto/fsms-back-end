package joel.fsms.modules.users.presention;

import joel.fsms.modules.users.domain.UserMapper;
import joel.fsms.modules.users.domain.UserQuery;
import joel.fsms.modules.users.domain.UserRequest;
import joel.fsms.modules.users.domain.UserResponse;
import joel.fsms.modules.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController{

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.update(id, userRequest)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.INSTANCE.toResponse(userService.create(userRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable, UserQuery userQuery) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.findAll(pageable, userQuery)));
    }
}
