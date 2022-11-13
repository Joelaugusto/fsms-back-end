package joel.fsms.modules.users.service;

import joel.fsms.modules.users.domain.UserRole;
import joel.fsms.modules.users.persistence.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleRepository repository;

    @Override
    public List<UserRole> findAll() {
        return repository.findAll();
    }

    @Override
    public UserRole findRole(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
