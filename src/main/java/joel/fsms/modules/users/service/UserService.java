package joel.fsms.modules.users.service;

import joel.fsms.config.file.domain.ImageBase64Request;
import joel.fsms.config.file.presentation.FileJson;
import joel.fsms.config.jwt.presentation.AuthTokenDto;
import joel.fsms.modules.users.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findById(Long id);
    User update(Long id, UserRequest userRequest);
    User create(UserRequest userRequest);
    AuthTokenDto create(UserCreateRequest userRequest, Long id);
    void delete(Long id);
    Page<User> findAll(Pageable pageable, UserQuery userQuery);
    Map<String, Boolean> verifyIfExists(UserUniqueConstraints uniqueConstraints);
    void sendEmailVerification(UserUniqueConstraints uniqueConstraints);
    List<UserMapMarker> findAllMapMarkers();

    FileJson updateProfilePhoto(ImageBase64Request image);
}
