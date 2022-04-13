package joel.fsms.modules.groups.persistence;

import joel.fsms.modules.groups.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
