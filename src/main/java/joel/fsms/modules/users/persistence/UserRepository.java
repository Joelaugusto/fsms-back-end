package joel.fsms.modules.users.persistence;

import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserMapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmailOrPhone(String email, String phone);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Set<User> findByIdIn(Collection<Long> ids);


    @Query("select new joel.fsms.modules.users.domain.UserMapMarker(u.id, u.name, u.role, u.address.latitude, u.address.longitude) from User u")
    List<UserMapMarker> findAllMapMarker();


}
