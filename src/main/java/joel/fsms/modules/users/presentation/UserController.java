package joel.fsms.modules.users.presentation;

import io.swagger.annotations.Api;
import joel.fsms.config.file.domain.ImageBase64Request;
import joel.fsms.config.file.presentation.FileJson;
import joel.fsms.config.jwt.presentation.AuthTokenDto;
import joel.fsms.modules.users.domain.*;
import joel.fsms.modules.users.service.UserRoleService;
import joel.fsms.modules.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User Management")
@CrossOrigin
public class UserController{

    private final UserServiceImpl userService;
    private final UserRoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.findById(id)));
    }

    @GetMapping("/unique")
    public ResponseEntity<Map<String, Boolean>> exists(UserUniqueConstraints uniqueConstraints) {
        return ResponseEntity.ok(userService.verifyIfExists(uniqueConstraints));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.update(id, userRequest)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.INSTANCE.toResponse(userService.create(userRequest)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AuthTokenDto> create(@RequestBody UserCreateRequest userRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(userRequest, id));
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

    @GetMapping("/roles")
    public ResponseEntity<List<UserRole>> findAllRole() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping("verify-email")
    public ResponseEntity<Void> sendEmail(@RequestBody UserUniqueConstraints constraints){
        userService.sendEmailVerification(constraints);
        return ResponseEntity.ok().build();
    }

    @PostMapping("verify-email/{token}")
    public ResponseEntity<UserResponse> verifyAccount(@PathVariable String token){
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(userService.verifyAccount(token)));
    }

    @GetMapping("map-markers")
    public ResponseEntity<List<UserMapMarker>> findAll() {
        return ResponseEntity.ok(userService.findAllMapMarkers());
    }

    @PutMapping("/profile-photo")
    public ResponseEntity<FileJson> uploadProfilePhoto(@RequestBody ImageBase64Request request){
        return ResponseEntity.ok(userService.updateProfilePhoto(request));
    }
}
