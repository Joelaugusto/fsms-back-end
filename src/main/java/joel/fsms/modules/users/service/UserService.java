package joel.fsms.modules.users.service;

import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserQuery;
import joel.fsms.modules.users.domain.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findById(Long id);
    User update(Long id, UserRequest userRequest);
    User create(UserRequest userRequest);
    void delete(Long id);
    Page<User> findAll(Pageable pageable, UserQuery userQuery);
}
