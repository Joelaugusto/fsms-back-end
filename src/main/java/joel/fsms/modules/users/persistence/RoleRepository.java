package joel.fsms.modules.users.persistence;

import joel.fsms.modules.users.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
}
