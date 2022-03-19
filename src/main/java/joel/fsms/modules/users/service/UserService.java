package joel.fsms.modules.users.service;

import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserQuery;
import joel.fsms.modules.users.domain.UserRequest;
import joel.fsms.modules.users.domain.UserUniqueConstraints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserService {

    User findById(Long id);
    User update(Long id, UserRequest userRequest);
    User create(UserRequest userRequest);
    void delete(Long id);
    Page<User> findAll(Pageable pageable, UserQuery userQuery);
    Map<String, Boolean> verifyIfExists(UserUniqueConstraints uniqueConstraints);
    void sendEmailVerification(UserUniqueConstraints uniqueConstraints);
}
