package joel.fsms.modules.users.service;

import joel.fsms.modules.users.domain.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> findAll();

    UserRole findRole(Long id);


}
