package joel.fsms.modules.users.persistence;

import joel.fsms.modules.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmailOrPhone(String email, String phone);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Set<User> findByIdIn(Collection<Long> ids);




}
